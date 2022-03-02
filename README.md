# Indicaciones
## Autorización
El token para el usario dado (challenge) con brypt es:
<br />
$2a$12$ZTFo.VPHUL6vCZG5uf25wejA9.gEGdM7.t7PFAAqov5NwwbnZkuuG
<br />
Para obternerlo hacemos la siguiente petición:
<br />
POST http://server/login
<br />
enviando lo siguiente:
`
{
    "username": "challenge",
    "password": "challenge"
}
`
<br />
y en la cabecera de la respuesta nos entrega el token.
### Deshabilitar la autorización
Descomentar la línea 17 y 18 del `application.yml`
## Endpoints
### Obtener productos
GET http://server/products
### Obtener solo un producto por ID
GET http://server/<id>
### Update de producto
POST http://server/
<br />
Dónde debe ser enviado el id, nombre, precio, sku y código en un json.
### Crear un nuevo producto
POST http://server/
<br />
Dónde debe ser enviado el nombre, precio, sku y código en un json dónde el sku y código no deben existir previamente en la base de datos.
### Borrar un producto
DELETE http://server/<id> 
