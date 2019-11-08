from flask import Flask,render_template, request,url_for,redirect
from sqlalchemy import create_engine
from sqlalchemy.ext.automap import automap_base
from sqlalchemy.orm import sessionmaker

app = Flask(__name__)
app.secret_key = 'Aula de BCD'

engine = create_engine("sqlite:///lab05.sqlite")
Session = sessionmaker(bind=engine)
Base = automap_base()
Base.prepare(engine,reflect=True)

Pessoa = Base.classes.Pessoa
Telefones = Base.classes.Telefones

@app.route('/')
def inicial():
    return render_template('index.html')

@app.route('/listar')
def listar():
    session = Session()
    pessoas = session.query(Pessoa).all()
    session.close()

    return render_template('listar.html',lista_pessoas = pessoas)

@app.route('/excluir',methods=['GET','POST'])
def excluir_pessoa():
    if request.method == 'GET':
        idP = str(request.args.get('id'))
        session = Session()
        p = session.query(Pessoa).filter(Pessoa.idPessoa == idP).first()
        session.close()

        return render_template('excluir.html', pessoa = p)
    else:
        idP = request.form['idP']
        session = Session()
        pessoa = session.query(Pessoa).filter(Pessoa.idPessoa == idP).first()

        # excluir lista de telefones relacionados a pessoa
        pessoa.telefones_collection[:]

        session.delete(pessoa)
        session.commit()
        session.close()

        return redirect(url_for('listar'))


@app.route('/cadastrar',methods=['GET','POST'])
def cadastrar_pessoa():
    if request.method == 'GET':
        return render_template('cadastrar.html')
    else:
        # obter os dados digitados nas caixas de texto
        nome = request.form['nome']
        telefone = request.form['tel']

        sessionSQL = Session()

        # inserindo na tabela os dados
        pessoa = Pessoa()
        pessoa.nome = nome
        tel = Telefones()
        tel.numero = telefone

        # fazendo o relacionamento
        pessoa.telefones_collection.append(tel)

        sessionSQL.add(pessoa)
        sessionSQL.commit()
        sessionSQL.close()

        return redirect(url_for('listar'))

@app.route('/editar',methods=['GET','POST'])
def editar_pessoa():
    if request.method == 'GET':

        idP = str(request.args.get('id'))
        session = Session()
        p = session.query(Pessoa).filter(Pessoa.idPessoa == idP).first()

        telefones = session.query(Telefones).filter(Telefones.idPessoa == idP).all()

        session.close()

        return render_template('editar.html', pessoa = p, telefones = telefones)
    else:

        idP = request.form['idP']
        sessionSQL = Session()
        pessoa = sessionSQL.query(Pessoa).filter(Pessoa.idPessoa == idP).first()

        nome = request.form['nome']
        pessoa.nome = nome

        for campo in request.form.items():
            if 'tel-' in campo[0]:
                # tel-234 -> idTel = 234
                idTel = campo[0].split('-')[1]
                for tel in pessoa.telefones_collection:
                    if tel.idTelefone == int(idTel):
                        tel.numero = campo[1]

        sessionSQL.commit()
        sessionSQL.close()

        return redirect(url_for('listar'))

if __name__ == '__main__':
    app.run(debug=True)
