#!/usr/bin/env python
# -*- coding: utf-8 -*

def main():
	html="""<div>            
            <img id="fondaso" src="http://i.imgur.com/eDF2PAt.jpg" alt=""/>
            <div id="fondogris"></div>
        </div>

        <div id="registrar">

        </div>
        <div id="envoltura">
            <div id="contenedor">

                <div id="cabecera">
                    <div>
                        <img src="http://i.imgur.com/dJ6OiJk.png" width="320" height="60" alt=""/>
                    </div>
                    <p style="color: black;"><b>REGISTRARSE</b></p>
                </div>

                <div id="cuerpo">

                    <form id="form-login" action="#" method="post" >
                        <p><label for="nombre">Nombre*:</label></p>
                        <input name="nombre" type="text" id="nombre" class="nombre" placeholder="Escriba su Nombre" autofocus=""/></p>
                        </br>
                        <!--=============================================================================================-->
                        <!--La sisguientes 2 líneas son para agregar campos al formulario con sus respectivos labels-->
                        <!--Puedes usar tantas como necesites-->
                        <p><label for="apellidos">Apellidos*:</label></p>
                        <input name="apellidos" type="text" id="apellidos" class="apellidos" placeholder="Escriba sus Apellidos" /></p>
                        <!--=============================================================================================-->
                        </br>
                        <p><label for="correo">Email*:</label></p>
                        <input name="correo" type="text" id="correo" class="correo" placeholder="Escriba su Email" /></p>
                        </br>
                        <p><label for="pass">Contraseña*:</label></p>
                        <p style="color: red;font-size: 10px" > (Debe contener mayúscula, minúscula y número)</p>
                        <input name="pass" type="password" id="pass" class="pass" placeholder="Escriba su Contraseña" /></p>
                        </br>
                        <p><label for="repass">Confirmar Contraseña*:</label></p>
                        <input name="repass" type="password" id="repass" class="repass" placeholder="Repita su Contraseña" /></p>

                        <p style="color: red">(*)Campos obligatorios</p>
                        <p id="bot"><input name="submit" type="submit" id="boton" value="Registrar" class="boton"/></p>
                    </form>
                </div>

                <div id="pie">¿Eres usuario de <b>ReparaSevilla</b>? <br /><a href="/">Inicia sesión aquí</a></div>
            </div><!-- fin contenedor -->

        </div>"""
	
	css = """<style>
body{
    background:#e0e0e0;
    /*background:#181907;*/
    font-family:Arial, Helvetica, sans-serif;
    font-size:12px;
    margin: 0;
}

#fondaso{
    position: absolute;
    z-index: -2;
    width: 100%;
    height: 100%;

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
    left:50%;
    top:50%;
    margin-left:-165px;
    margin-top: -300px;
    width: 330px;
 
}
 
#contenedor{
    background-color:#ff765e;
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
    height:120px;
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
    width: 230px;
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
    font-size: 16px;
    height: 50px;
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

}</style>"""

	return css + html
