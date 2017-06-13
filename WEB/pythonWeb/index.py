#!/usr/bin/env python
# -*- coding: utf-8 -*
from flask import Flask, request
from flask import send_from_directory, current_app
import base64, os, time, login, formularioRegistro, adminIncidencias, editarPerfil, mapaIncidencias, misIncidencias, subirIncidencia, inicio
app = Flask(__name__ , static_folder="static")
app.secret_key = 'MGP'
from flask_images import Images
images = Images(app)

def head():
	return """<ul>
            <li><img src="http://i.imgur.com/dJ6OiJk.png" height="40" width="150" style="margin-left: 5px;" alt=""/></li>
            <li><a href="inicio">Inicio</a></li>
            <li><a href="subirIncidencia">Subir Incidencia</a></li>
            <li><a href="misIncidencias">Mis Incidencias</a></li>
            <li><a href="mapaIncidencias">Mapa de Incidencias</a></li>
            <li><a href="editarPerfil">Mi Perfil</a></li>
            <li><a href="/">Cerrar sesión</a></li>
            <li><a href="adminIncidencias">Panel ADMIN</a></li>
        </ul>

        <div>            
            <img id="fondaso" src="http://i.imgur.com/eDF2PAt.jpg" alt=""/>
            <div id="fondogris"></div>
        </div>"""

@app.route('/')
def indexlogin():

	www = "<body>" + login.main() + "</body>"
	return www

@app.route('/inicio')
def indexinicio():
	
	
	www = "<body>"+ head() + inicio.main() + "</body>"
	return www

@app.route('/adminIncidencias')
def indexadminIncidencias():
	
	www = "<body>"+ head() + adminIncidencias.main() + "</body>"
	return www

@app.route('/editarPerfil')
def indexeditarPerfil():
	
	www = "<body>"+ head() + editarPerfil.main() + "</body>"
	return www

@app.route('/formularioRegistro')
def indexformularioRegistro():
	

	www = "<body>" + formularioRegistro.main() + "</body>"
	return www

@app.route('/mapaIncidencias')
def indexmapaIncidencias():
	
	www = "<body>"+ head() + mapaIncidencias.main() + "</body>"
	return www

@app.route('/misIncidencias')
def indexmis_incidencias():
	
	www = "<body>"+ head() + misIncidencias.main() + "</body>"
	return www

@app.route('/subirIncidencia')
def indexsubirIncidencia():
	
	www = "<body>"+ head() + subirIncidencia.main() + "</body>"
	return www

if __name__ == '__main__':
	app.run(host='0.0.0.0', debug=True, processes=3)
