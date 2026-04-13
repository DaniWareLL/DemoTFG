[Requisitos previos para correcto funcionamiento] -> 

- Contenedor Docker con archivo .yml adjuntado en el zip para persistencia (PostgreSQL)
- Primera ejecución para que ORM mapee tablas: Linea 31 persistence.xml = value="create"
- Resto de ejecuciones: Linea 31 persistence.xml = value="none"
- VLC instalado en la ruta por defecto (S.O Ubuntu/WIN) (OBLIGATORIO 64b version)


[MAINS] ->

- GUI del Login + persistencia de User.class: GUILoginStarter.java
- Prueba de servicios: MainServices.java


[GITHUB] -> https://github.com/DaniWareLL/TFG_Dlillo_Jmartin/tree/estructura-proyecto

(La Branch "Master" es una versión antigua del proyecto)