#!/usr/bin/env python
# -*- coding: utf-8 -*
import requests, json
def main(email):
	html = """
        <script>
            function myMap() {
                var mapOptions = {
                    center: new google.maps.LatLng(51.5, -0.12),
                    zoom: 10,
                    mapTypeId: google.maps.MapTypeId.HYBRID
                }
                var map = new google.maps.Map(document.getElementById("map"), mapOptions);
            }
        </script>
        <div id="registrar">
        </div>
        <div id="envoltura">
            <div id="contenedor">
                <div id="cabecera">
                    MIS INCIDENCIAS
                </div>
                <div id="contenedorscroll">
                    <div id="cuerpo">
                        <table border="0">
                            <tbody>
    """
	css = """<style>
body{
    /*background:#e0e0e0;*/
    /*background:#181907;*/
    font-family:Arial, Helvetica, sans-serif;
    font-size:12px;
    margin: 0;
    overflow-x: hidden;
    overflow-y: scroll;
    /*overflow:scroll;*/
    background-repeat:no-repeat;
    width: 100%; 
    height: 100%;
    top:0;
    left:0;
    position:absolute;
    z-index: -1;   



}

#fondaso{
    position: absolute;
    z-index: -2;
    width: 100%;
    height: 100%;

}

#tablaincidencias{
    width: 400px;
    height: 250px;
}

#contenedorscroll{
    overflow: hidden;
    overflow-y: scroll;
    height: 500px;
    width: 500px;
}

#fondogris{
    background: gray;
    opacity: 0.7;
    position: absolute;
    z-index: -1;
    width: 100%;
    height: 100%;
}

#registrar{
    float:left;
    width:90%;
    font-size:10px;
    font-weight:bold;
    color:#FFF;
    text-align:right;
}

#envoltura{
    position:absolute;
    /*left y top al 50% para que quede centrado en la pantalla*/
    left:45%;
    top:40%;
    margin-left:-165px;
    margin-top: -210px;
    width: 500px;

}

#contenedor{
    background-color:#ff765e;
    z-index: 1;
    /*background-color:#356AA0;*/
    /*box-shadow: 0 0 0 9px rgba(128,0,0,.1);*/
    /*Margen de sombra alrededor del contenedor 8px negro*/
    box-shadow: 3px 3px 10px 5px rgba(0,0,0,.8);
    /*Las 3 líneas siguientes, sirven para el borde redondeado
     * pero para diferentes navegadores*/
    -webkit-border-radius:5px;
    -moz-border-radius:10px;
    border-radius:10px;
}

#cabecera{
    /*linea azul que separa logo*/
    border-bottom: 5px solid #333;
    padding-top: 5px;
    /*color:#FFF;*/
    height:50px;
    line-height:50px;
    text-align:center;
    font-size: 30px;
    color: #333;
}

#cuerpo{
    background:#ffffff;
    border:solid #ccc;
    /*aumentando el 2px 'aparece' un borde*/
    border-width: 2px 0;
    padding:15px 35px;
}

label{
    color: #666;
    font-weight: bold
}

input{
    /*border: 1px solid #999; No usar esta línea y dejar box-shadow hace efecto de profundidad*/
    border-radius:5px; /*probar con 10px se hacen ovalados los inputs*/
    box-shadow: 2px 2px 3px 1px rgba(0,0,0,.8);
    font:bold 12px Arial, Helvetica, sans-serif;
    height: 24px;
    line-height: 20px;
    padding:0 2px;
    width: 190px;
}

input#usuario{
    background:#ffc url(./images/loquito.jpg) no-repeat 0 2px; /*probar con #ddf.
                                                                Para mover arriba-abajo la imagen 'jugar' con 2px, 5px */
    /*Para que el texto dentro del input se mueva*/
    padding-left: 25px; /*Sirve para darle espacio ala imagen, probar con 30px 10px y ver comportamiento*/
}

input#contrasenia{
    background:#ffc url(./images/infinio.jpg) no-repeat 0 5px;
    padding-left: 25px;

}

.boton{
    background: #5eff70;
    /*background: -moz-linear-gradient(top,#eee,#ccc);*/
    /*background: -moz-linear-gradient(top,#fc6,#f63);*/

    /*Color del texto*/
    color: #333;
    width: 120px;
}

/*Tip estas instrucciones animan al boton enviar
 * para que se vea como si se pulsara*/
.boton:active{
    position: relative;
    top: 3px;
}

#pie{
    border-top: 5px solid #333;
    color: #333;
    font-size: 11px;
    height: 27px;
    line-height: 24px;
    text-align: center;
}

form,p{
    margin:0;
}

p{
    /*Para que los elementos del cuerpo
     * se separen entre ellos probar con
     * 10px 20px                      */
    padding-bottom: 5px;
}

/*Para separar el botón de ingresar*/
p#bot{
    padding-top: 10px;
}

.boton#editarnombre{
    width: 50px;
    background: #b5efff;
    margin-left: 9px;
    height: 24px;
}

ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #ff765e;
    position: fixed;
    top: 0;
    width: 100%;

}

li {
    float: left;

}

li a {
    display: block;
    color: #333;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

/* Change the link color to #111 (black) on hover */
li a:hover {
    background-color: #333;
    color:#e0e0e0;

}
</style>"""

	json_mail = {'email':email}
	ru = requests.post('http://192.168.0.105:5001/get/incidencias', json=json_mail)
	dic_incidencias = ru.content
	list_incidencias = json.loads(dic_incidencias)
	list_dic = []
	for i in range(len(list_incidencias)):
		list_dic.append(str(list_incidencias.get(str(i))))
	for dics in list_dic:
		dic = json.loads(dics)
		decode = dic['imagen'].decode()
		html += """<tr>
                                    <td>
                                        <div id="incidencia" style="/*margin-left: 80px;*/
                                             border-radius: 10px 10px 10px 10px;
                                             -moz-border-radius: 10px 10px 10px 10px;
                                             -webkit-border-radius: 10px 10px 10px 10px;
                                             border: 4px solid #333;
                                             background: #ffa191;
                                             ">

                                            <table id="tablaincidencias" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                            <p><label><b>Dirección: </b></label><br />
                                                                <label for="direccion">"""+str(dic['direccion'])+"""</label>
                                                            </p>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <label><b>Descripción: </b></label><br />
                                                            <label for="descripcion">"""+str(dic['descripcion'])+"""</label>
                                                            <br />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td><br />
                                                            <p><label for="estado"><b>Estado: </b></label></p>                                                        
                                                            <label for="estado" style="color: green;">"""+str(dic['estado'])+"""</label>
                                                            <br /><br />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <p><label for="foto"><b>Foto: </b></label></p>
                                                            <p><img style="margin-left: 80px;
                                                                    border-radius: 25px 25px 25px 25px;
                                                                    -moz-border-radius: 25px 25px 25px 25px;
                                                                    -webkit-border-radius: 25px 25px 25px 25px;
                                                                    border: 4px solid #333;" src='data:image/png;base64,{0}' height="130px" width="230px">""".format(decode)+"""</p>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <p><label for="ubicacion"><b>Ubicación:</b></label></p>
                                                            <br>
                                                            <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d12698.051094657849!2d"""+str(dic['latitud'])+"""!3d"""+str(dic['longitud'])+"""!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1ses!2ses!4v1496169632482" style="border:0" allowfullscreen="" frameborder="0" height="150" width="390"></iframe>

                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </td>
                                </tr>"""

	html += """
                            </tbody>
                        </table>

                    </div>
                </div>

                <div id="pie">
                    <div>
                        <img src="http://i.imgur.com/dJ6OiJk.png" width="120" height="25" alt=""/>
                    </div>
                </div>
            </div><!-- fin contenedor -->
            <!--fondo gris-->
        </div>
"""

	return css + html
