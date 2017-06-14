#!/usr/bin/env python
# -*- coding: utf-8 -*
import requests, json
def main():
	html = """<div>            
            <img id="fondaso" src="http://i.imgur.com/eDF2PAt.jpg" alt=""/>
            <div id="fondogris"></div>
        </div>
        <!-- fin opcion-->
        <div id="envoltura">
            <div id="contenedor">
                <div id="cabecera" >
                    <div>
                        <img src="http://i.imgur.com/dJ6OiJk.png" width="320" height="60" alt=""/>
                    </div>
                    <p style="color: black;"><b>INICIAR SESIÓN</b></p>                   
                </div>
                <div id="cuerpo">
                    <form id="form-login" action="" method="post" autocomplete="off">
                        <!--A saber, el atributo for funciona como el id.-->
                        <!--ejemplo <label for="usuario">Usuario:</label>-->
                        <!--required es nuevo en html5, si el campo está vacío te avisa, pero cuidado, no valida la información-->
                        <p><label >Email:</label></p>
                        <input name="email" type="text" id="email" placeholder="Introduzca su Email" autofocus="" required=""/>
                        </br></br></br>
                        <p><label>Contraseña:</label></p>
                        <input name="pass" type="password" id="pass" placeholder="Introduzca su Contraseña" required=""/>
                        <p id="bot"><input type="submit" id="submit" name="submit" value="Ingresar" class="boton" ></p>
                    </form>
                </div><!--fin cuerpo-->
                <div id="pie">¿No eres usuario de <b>ReparaSevilla</b>? <a href="formularioRegistro">Regístrate aquí</a></div>
            </div><!-- fin contenedor -->
        </div><!--fin envoltura-->"""
	css = """<style>
body{
    background:#e0e0e0;
    /*background:#181907;*/
    font-family:Arial, Helvetica, sans-serif;
    font-size:12px;
    margin: 0;
}
 
#registrar{
        float:left;
        width:90%;
        margin-top: 15px;
        font-size:13px;
        font-weight:bold;
        color:#333;
        text-align:center;
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
 
#envoltura{
        position:absolute;
    /*left y top al 50% para que quede centrado en la pantalla*/
    left:50%;
    top:50%;
    margin-left:-165px;
    margin-top: -210px;
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
</style>"""

	return css+html
