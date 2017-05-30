#!/usr/bin/env python
# -*- coding: utf-8 -*
from flask import Flask, request, jsonify, json
import MySQLdb
app = Flask(__name__)

db = MySQLdb.connect(host="localhost", user="root", passwd="toor", db="magikproyectBD")


@app.route('/get/emails', methods=['GET', 'POST'])
def obtener_emails():
	cursor = db.cursor()
	try:
		count = 0
		data = {}
		sql = """SELECT email FROM Usuario;"""
		cursor.execute(sql)
		resultados = cursor.fetchone()
		list_resultados = list(resultados)
		for i in list_resultados:
			data +={"'"+count+"'":i}
			count +=1
		cursor.close()
		return json.dumps(data)
	except:
		cursor.close()
		return "Unable to insert data on database."



@app.route('/get/usuario', methods=['GET', 'POST'])
def obtener_usuario():
		cursor = db.cursor()
	#try:
		data= dict()
		r = request.get_json()
		email = r.get("email")
		sql = "SELECT * FROM Usuario WHERE email='"+email+"'"";"
		cursor.execute(sql)
		resultados = cursor.fetchone()
		list_resultados = list(resultados)
		ids = list_resultados[0]
		nombre = list_resultados[1]
		apellidos = list_resultados[2]
		password = list_resultados[4]
		admin = list_resultados[5]
		imagenPerfil = list_resultados[6]

		data={"id":ids,"nombre":nombre, "apellidos":apellidos, "email":email, "password":password, "admin":admin, "imagenPerfil":imagenPerfil}
		print data
		cursor.close()
		return json.dumps(data)
#	except:
#		print 'peta'
#		cursor.close()
#		return "Unable to insert data on database."


@app.route('/get/incidencias', methods=['GET'])
def obtener_incidencias():
	cursor = db.cursor()
	try:
		r = request.get_json()
		email = r.get("email")
		sql = """SELECT * FROM Incidencias WHERE email="""+email+""";"""
		cursor.execute(sql)
		resultados = cursor.fetchall()
		list_data=[]
		for resultado in resultados:
			list_resultados = list(resultados)
			ids= list_resultados[0]
			descripcion = list_resultados[1]
			direccion = list_resultados[2]
			imagen = list_resultados[3]
			latitud = list_resultados[4]
			longitud = list_resultados[5]
			estado = list_resultados[6]
			data={"id":ids, "descripcion":descripcion, "direccion":direccion, "imagen":imagen, "latitud":latitud, "longitud":longitud, "email":email, "estado":estado}
			list_data.append(data)
		cursor.close()
		return json.dumps(list_data)
	except:
		cursor.close()
		return "Unable to insert data on database."




@app.route('/post/usuario', methods=['POST'])
def insertar_usuario():
		cursor = db.cursor()
		r = request.get_json()
		nombre = r.get("nombre")
		apellidos = r.get("apellidos")
		email = r.get("email")
		password = r.get("password")
		admin = r.get("admin")
		imagenPerfil = r.get("imagenPerfil")
		sql = """INSERT INTO Usuario (nombre, apellidos, email, password, admin, imagenPerfil) VALUES ('"""+nombre+"""', '"""+apellidos+"""', '"""+email+"""', '"""+password+"""', '"""+admin+"""', '"""+imagenPerfil+"""');"""
		try:
			cursor.execute(sql)
			db.commit()
		except:
			db.rollback()
		cursor.close()
		return "Successfully inserted on database."


@app.route('/post/incidencias', methods=['POST'])
def insertar_incidencias():
		cursor = db.cursor()
		r = request.get_json()
		descripcion = r.get("descripcion")
		direccion = r.get("direccion")
		imagen = r.get("imagen")
		latitud = r.get("latitud")
		longitud = r.get("longitud")
		email = r.get("email")
		estado = r.get("estado")
		print direccion, longitud, latitud
		sql = """INSERT INTO Incidencias (descripcion, direccion, imagen, latitud, longitud, email, estado) VALUES ('"""+descripcion+"""', '"""+direccion+"""', '"""+imagen+"""', '"""+latitud+"""', '"""+longitud+"""', '"""+email+"""','"""+estado+"""');"""
		try:
			cursor.execute(sql)
			db.commit()
		except:
			db.rollback()
		cursor.close()
		return "Successfully inserted on database."


@app.route('/put/usuario', methods=['POST'])
def actualizar_usuario():
	cursor = db.cursor()
	try:
		r = request.get_json()
		idi = r.get("id")
		nombre = r.get("nombre")
		apellidos = r.get("apellidos")
		email = r.get("email")
		password = r.get("password")
		admin = r.get("admin")
		imagenPerfil = r.get("imagenPerfil")
		sql = """UPDATE Usuario SET nombre = """+nombre+""", apellidos = """+apellidos+""", email = """+email+""", password = """+password+""", admin = """+admin+""", imagenPerfil = """+imagenPerfil+""" WHERE id ="""+ids+""";"""
		try:
			cursor.execute(sql)
			db.commit()
		except:
			db.rollback()
		cursor.close()
		return "Successfully inserted on database."
	except:
		cursor.close()
		return "Unable to insert data on database."




@app.route('/del/incidencias', methods=['POST'])
def borrar_incidencias():
	cursor = db.cursor()
	try:
		r = request.get_json()
		idi = r.get("id")
		sql = """DELETE FROM Incidencias WHERE id="""+idi+""";"""
		try:
			cursor.execute(sql)
			db.commit()
		except:
			db.rollback()
		cursor.close()
		return "Successfully inserted on database."
	except:
		cursor.close()
		return "Unable to insert data on database."




if __name__ == '__main__':
	app.run(host='0.0.0.0', debug=True, port=5001)
