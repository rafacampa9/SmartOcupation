Trabajo de Desarrollo de Interfaces de 2º DAM

Se trata de una aplicación para gestionar el alquiler de viviendas. 
He conectado con una BBDD relacional, cuyo SGBD es PostgreSQL, y que
está compuesto de cuatro tablas: ARRENDAMIENTOS, CLIENTES, VIVIENDAS
y PROPIETARIOS. 

Para esta aplicación, se realizan los métodos CRUD en las cuatro tablas
si ingresas a la app como administrador y, en caso de entrar como empleado, 
varias consultas multitabla.

Para poder integrar el JasperReport en el proyecto, he necesitado agregar
las siguientes librerías:
  - bcprov-jdk15on-1.54.jar
  - commons-beanutils-1.9.2.jar
  - commons-collections4-4.1.jar
  - commons-collections4-4.0.jar
  - commons-digester-2.1.jar
  - commons-logging-1.1.1.jar
  - ecj-3.21.0.jar
  - itext-2.1.7.jar
  - jackson-annotations-2.13.3.jar
  - jackson-core-2.13.3.jar
  - jackson-databind-2.13-3.jar
  - jackson-dataformat-xml-2.13.3.jar
  - jcommon-1.0.23.jar
  - jfreechart-1.0.19.jar
  - commons-collections4-4.4.jar
  - commons-digester3-3.2.jar
  - commons-logging-1.2.jar
  - jasperreports-6.20.6.jar

Además, es necesario incluir el driver de PostgreSQL (postgresql-42.6.0.jar), 
las librerías para la clase JCalendar (ya que el componente JDateChooser es
importado del paquete "com.toedter.calendar.JDateChooser") y las de JUnit 5.6.0
