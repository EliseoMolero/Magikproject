#!/usr/bin/env python
# -*- coding: utf-8 -*
from flask import Flask, request, jsonify, json
import MySQLdb
app = Flask(__name__)

db = MySQLdb.connect(host="localhost", user="root", passwd="toor", db="magikproyectBD")


@app.route('/get/emails', methods=['POST', 'GET'])
def obtener_emails():
	cursor = db.cursor()
	try:
		count = 0
		data = {}
		sql = """SELECT email FROM Usuario;"""
		cursor.execute(sql)
		resultados = cursor.fetchall()
		list_resultados = list(resultados)
		for i in list_resultados:
			name = str(count)
			data[name]=i
			count +=1
		cursor.close()
		print data
		return json.dumps(data)
	except:
		cursor.close()
		return "Unable to insert data on database."


@app.route('/get/fincidencias', methods=['POST', 'GET'])
def obtener_fincidencia():
	cursor = db.cursor()
	try:
		count = 0
		data = {}
		sql = """SELECT * FROM Incidencias;"""
		cursor.execute(sql)
		resultados = cursor.fetchall()
		list_data=[]
		dict_data = {}
		count = 0
		for resultado in resultados:
			ids= resultado[0]
			descripcion = resultado[1]
			direccion = resultado[2]
			estado = resultado[6]
			data={"id":ids, "descripcion":descripcion, "direccion":direccion, "estado":estado}
			list_data.append(data)
		for i in list_data:
			name = str(count)
			dict_data[name]=json.dumps(i)
			count +=1
		cursor.close()
		return json.dumps(dict_data)
	except:
		print 'peta'
		cursor.close()
		return "Unable to insert data on database."



@app.route('/get/usuario', methods=['GET', 'POST'])
def obtener_usuario():
	cursor = db.cursor()
	try:
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
	except:
		cursor.close()
		data={"id":'',"nombre":'', "apellidos":'', "email":'', "password":'', "admin":'', "imagenPerfil":''}
		return json.dumps(data)

@app.route('/get/usuarioId', methods=['GET', 'POST'])
def obtener_usuarioID():
	cursor = db.cursor()
	try:
		data= dict()
		r = request.get_json()
		ids = r.get("id")
		sql = "SELECT * FROM Usuario WHERE idUsuario='"+ids+"'"";"
		cursor.execute(sql)
		resultados = cursor.fetchone()
		list_resultados = list(resultados)
		email = list_resultados[3]
		nombre = list_resultados[1]
		apellidos = list_resultados[2]
		password = list_resultados[4]
		admin = list_resultados[5]
		imagenPerfil = list_resultados[6]

		data={"id":ids,"nombre":nombre, "apellidos":apellidos, "email":email, "password":password, "admin":admin, "imagenPerfil":imagenPerfil}
		print data
		cursor.close()
		return json.dumps(data)
	except:
		cursor.close()
		data={"id":'',"nombre":'', "apellidos":'', "email":'', "password":'', "admin":'', "imagenPerfil":''}
		return json.dumps(data)

@app.route('/get/emailId', methods=['GET', 'POST'])
def obtener_emailID():
	cursor = db.cursor()
	try:
		data= dict()
		r = request.get_json()
		ids = r.get("id")
		sql = "SELECT * FROM Usuario WHERE idUsuario='"+str(ids)+"'"";"
		cursor.execute(sql)
		resultados = cursor.fetchone()
		list_resultados = list(resultados)
		email = list_resultados[3]
		data={"email":email}
		print data
		cursor.close()
		return json.dumps(data)
	except:
		cursor.close()
		data={"id":'',"nombre":'', "apellidos":'', "email":'', "password":'', "admin":'', "imagenPerfil":''}
		return json.dumps(data)

@app.route('/get/incidencias', methods=['POST'])
def obtener_incidencias():
	cursor = db.cursor()
	try:
		dict_data ={}
		r = request.get_json()
		email = r.get("email")
		sql = "SELECT * FROM Incidencias WHERE email='"+email+"'"";"
		cursor.execute(sql)
		resultados = cursor.fetchall()
		list_data=[]
		count = 0
		for resultado in resultados:
			ids= resultado[0]
			descripcion = resultado[1]
			direccion = resultado[2]
			imagen = resultado[3]
			latitud = str(resultado[4])
			longitud = str(resultado[5])
			estado = resultado[7]
			data={"id":ids, "descripcion":descripcion, "direccion":direccion, "imagen":imagen, "latitud":latitud, "longitud":longitud, "email":email, "estado":estado}
			list_data.append(data)
		for i in list_data:
			name = str(count)
			dict_data[name]=json.dumps(i)
			count +=1
		cursor.close()
		return json.dumps(dict_data)
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
		try:
			admin = r.get("admin")
		except:
			admin = '1'
		try:
			imagenPerfil = r.get("imagenPerfil")
		except:
			imagenPerfil = 'img'
		sql = """INSERT INTO Usuario (nombre, apellidos, email, password, admin, imagenPerfil) VALUES ('"""+str(nombre)+"""', '"""+str(apellidos)+"""', '"""+str(email)+"""', '"""+str(password)+"""', '"""+str(admin)+"""', '"""+str(imagenPerfil)+"""');"""
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
		if latitud == None:
			latitud = 0
		longitud = r.get("longitud")
		if longitud == None:
			longitud = 0
		email = r.get("email")
		try:
			estado = r.get("estado")
		except:
			estado = 'No revisado'
		sql = """INSERT INTO Incidencias (descripcion, direccion, imagen, latitud, longitud, email, estado) VALUES ('"""+str(descripcion)+"""', '"""+str(direccion)+"""', '"""+str(imagen)+"""', '"""+str(latitud)+"""', '"""+str(longitud)+"""', '"""+str(email)+"""','"""+str(estado)+"""');"""
#		try:
		cursor.execute(sql)
		db.commit()
#		except:
#			db.rollback()
		cursor.close()
		return "Successfully inserted on database."


@app.route('/put/usuario', methods=['POST'])
def actualizar_usuario():
	cursor = db.cursor()
	try:
		r = request.get_json()
		ids = r.get("id")
		nombre = r.get("nombre")
		apellidos = r.get("apellidos")
		email = r.get("email")
		password = r.get("password")
		try:
			admin = r.get("admin")
		except:
			admin = '1'
		try:
			imagenPerfil = r.get("imagenPerfil")
		except:
			imagenPerfil = 'img'
		sql = """UPDATE Usuario SET nombre = '"""+nombre+"""', apellidos = '"""+apellidos+"""', email = '"""+email+"""', password = '"""+password+"""', admin = '"""+admin+"""', imagenPerfil = '"""+imagenPerfil+"""' WHERE idUsuario ='"""+ids+"""';"""
		try:
			cursor.execute(sql)
			db.commit()
		except:
			db.rollback()
		cursor.close()
		return "Successfully inserted on database."
	except:
		print 'peta'
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
