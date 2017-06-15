#!/usr/bin/env python
# -*- coding: utf-8 -*
from flask import Flask, request
from flask import send_from_directory, current_app
import base64, os, time, login, formularioRegistro, adminIncidencias, editarPerfil, mapaIncidencias, misIncidencias, subirIncidencia, inicio, json, requests
app = Flask(__name__ , static_folder="static")
app.secret_key = 'MGP'
from flask_images import Images
images = Images(app)


def head(email):
	return """<head>
        <title>Repara Sevilla</title>
        <meta charset="utf-8">
        
    </head>
<ul>
            <li><img src="http://i.imgur.com/dJ6OiJk.png" height="40" width="150" style="margin-left: 5px;" alt=""/></li>
            <li><a href="/inicioC/"""+str(email)+"""">Inicio</a></li>
            <li><a href="/subirIncidencia/"""+str(email)+""""">Subir Incidencia</a></li>
            <li><a href="/misIncidencias/"""+str(email)+"""">Mis Incidencias</a></li>
            <li><a href="/mapaIncidencias/"""+str(email)+"""">Mapa de Incidencias</a></li>
            <li><a href="/editarPerfil/"""+str(email)+"""">Mi Perfil</a></li>
            <li><a href="/">Cerrar sesión</a></li>
            <li><a href="/adminIncidencias/"""+str(email)+"""">Panel ADMIN</a></li>
        </ul>

        <div>            
            <img id="fondaso" src="http://i.imgur.com/eDF2PAt.jpg" alt=""/>
            <div id="fondogris"></div>
        </div>"""

@app.route('/')
def indexlogin():

	www = "<body>" + login.main() + "</body>"
	return www

@app.route('/inicioR', methods=['POST'])
def indexinicioR():
	
	try:
		nombre = request.form['nombre']
		apellidos = request.form['apellidos']
		email = request.form['email']
		password = request.form['pass']
		repass = request.form['repass']
		if password == repass:
			json_usuario = {'nombre':nombre, 'apellidos':apellidos, 'email':email, 'password':password}
			requests.post('http://192.168.0.105:5001/post/usuario', json=json_usuario)
			www = "<body>"+ head(email) + inicio.main(email) + "</body>"
		else:
			www = "<body>" + login.main() + "</body>"
		return www
	except:
		www = "<body>" + login.main() + "</body>"
		return www

@app.route('/inicioC/<email>')
def indexinicioC(email):

	www = "<body>"+ head(email) + inicio.main(email) + "</body>"
	return www

@app.route('/', methods=['POST'])
def indexinicio():
	email = request.form['email']
	password = request.form['pass']
	r = requests.get('http://192.168.0.105:5001/get/emails')
	email_dic = r.content
	email_json = json.loads(email_dic)
	emails = []
	www = ''
	for i in range(len(email_json)):
		split_mail = str(email_json.get(str(i)))
		list_mail = split_mail.split('\'')
		emails.append(list_mail[1])
	if email not in emails:
		www += "<body>" + login.main() + "</body>"
	else:
		json_mail = {'email':email}
		ru = requests.post('http://192.168.0.105:5001/get/usuario', json=json_mail)
		user_dic = ru.content
		user_json = json.loads(user_dic)
		passwordDB = user_json.get('password')
		if password == passwordDB:
			www = "<body>"+ head(email) + inicio.main(email) + "</body>"
		else:
			www += "<body>" + login.main() + "</body>"
	return www

@app.route('/okP', methods=['POST'])
def indexinicioP():
	nombre = request.form['nombre']
	apellidos = request.form['apellidos']
	email = request.form['email']
	password = request.form['pass']
	repass = request.form['repass']
	img = request.form['subirimagen']
	if password == repass:
		json_usuario = {'nombre':nombre, 'apellidos':apellidos, 'email':email, 'password':password, 'imagenPerfil':img }
		requests.post('http://192.168.0.105:5001/put/usuario', json=json_usuario)
		www = "<body>"+ head(email) + inicio.main(email) + "</body>"
	else:
		www = "<body>"+ head(email) + inicio.main(email) + "</body>"
	return www

@app.route('/adminIncidencias/<email>')
def indexadminIncidencias(email):
	
	www = "<body>"+ head(email) + adminIncidencias.main() + "</body>"
	return www

@app.route('/editarPerfil/<email>')
def indexeditarPerfil(email):
	
	www = "<body>"+ head(email) + editarPerfil.main(email) + "</body>"
	return www

@app.route('/formularioRegistro')
def indexformularioRegistro():
	

	www = "<body>" + formularioRegistro.main() + "</body>"
	return www

@app.route('/mapaIncidencias/<email>')
def indexmapaIncidencias(email):
	
	www = "<body onLoad='myMap()'>"+ head(email) + mapaIncidencias.main(email) + "</body>"
	return www

@app.route('/misIncidencias/<email>')
def indexmis_incidencias(email):

	www = "<body>"+ head(email) + misIncidencias.main(email) + "</body>"
	return www

@app.route('/subirIncidencia/<email>')
def indexsubirIncidencia(email):
	
	www = """<body onload="myMap()">"""+ head(email) + subirIncidencia.main(email) + "</body>"
	return www

@app.route('/ok', methods=['POST'])
def indexOk():
	direccion = request.form['direccion']
	imagen = request.form['subirimagen']
	descripcion = request.form['descripcion']
	email = request.form['email']
	try:
		json_incidencia = {'direccion':direccion, 'descripcion':descripcion, 'email':email, 'imagen':imagen}
		requests.post('http://192.168.0.105:5001/post/incidencias', json=json_incidencia)
		www = "<body>"+ head(email) + inicio.main(email) + "</body>"
	except:
		www = "<body>"+ head(email) + inicio.main() + "</body>"
	return www

if __name__ == '__main__':
	app.run(host='0.0.0.0', debug=True, processes=3)
