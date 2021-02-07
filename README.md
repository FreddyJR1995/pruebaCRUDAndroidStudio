# pruebaCRUDAndroidStudio</br>
<h3>Freddy Valverde</h3></br>
<h3>Desarrollo de Aplicaciones Móviles</h3></br>
<h1>Video explicativo del funcionamiento de la aplicación</h1></br>
<a href="https://www.youtube.com/watch?v=tJT5OmoZI0Y">Click Aqui para ver</a></br>
<h1>Configuración de Firebase y Android Studio</h1></br>
<h2>En Firebase</h2></br>
<p>Se accedio a la consola de firebase, se creó un proyecto, seguido de una aplicación para android, aqui el asistente de firebase nos pide el nombre del paquete de la aplicación, una cadena de sha1 que la generamos en nuestro proyecto con gradle. A continuación se solicita que descarguemos el archivo google-services.json y lo peguemos en la carpeta indicada por el asistente de firebase. Finalmente se añadio las dependecias de firebase en gradle a nivel de proyecto y de app.</p></br>
<h2>En Android Studio</h2></br>
<p>El asistente de firebase nos indica donde copiamos el archivo google-services.json y donde añadimso las dependencias. Finalmente corremos la aplicación</p></br>
<p align="center">
  <img src="/Capturas/depenProyecto.JPG" width="500"></br>
  <img src="/Capturas/depenApp.JPG" width="500">
</p>
<h1>MainActivity</h1></br>
<p>Esta es la actividad donde se desarrollo el registro de usuarios, principalmente lo que realiza es usar FirebaseAuth para la autenticación y un DatabaseReference para mandar los datos a una colección de usuarios en RealtimeDatabase. A continuación se mostrará la imagen de dicha pantalla</p></br>
<p align="center">
  <img src="/imagenes/imgRegistro.jpg" width="350">
</p>
<h2>Funciones</h2></br>
<h3>OnCreate</h3></br>
<p>Aprovechando que el onCreate se ejecuta al iniciar la actividad, se creo un evento para pasar los datos a unas variables, y llamar una función de registro siempre que se de click sobre el botón.</p></br>
<p align="center">
  <img src="/Capturas/mainDos.JPG" width="500">
</p>
<h3>registerUser</h3></br>
<p>Esta función permite crear el registro y pasar los datos a la colección. Aquí se llama al método createUserWithEmailAndPassword del objeto FirebaseAuth, adjuntandole un evento que valide si dicha tarea se completo, si es asi, crreará un objeto Map de tipo Strign con los datos que guardaremos. A continuación con la referencia a Database guardaremos estos datos, a la refererencia se le adjunta un evento de validación, aqui si esta tarea es exitosa nos manda a la ventana de Perfil.</p></br>
<p align="center">
  <img src="/Capturas/mainTres.JPG" width="500">
</p>
<h3>passLogin</h3></br>
<p>Esta función permite ir a la ventan a de inicio de sesión</p></br>
<p align="center">
  <img src="/Capturas/mainCuatro.JPG" width="500">
</p>
<h3>onStart</h3></br>
<p>Esta función controla si ya existe una sesión iniciada en base al metodo getCurrentUser del objeto FirebaseAuth para en el caso de ya haber iniciado sesión no vuleva a ingresar los datos del login. </p></br>
<p align="center">
  <img src="/Capturas/mainCinco.JPG" width="500">
</p>
<h1>LoginActivity</h1></br>
<p>Esta actividad permite iniciar sesión usadno de igual forma FirebaseAuth. A continuación se puede ver la venta de inicio de sesión.</p></br>
<p align="center">
  <img src="/imagenes/imgInicio.jpg" width="350">
</p>
<h2>Funciones</h2></br>
<h3>onCreate</h3></br>
<p>De igual forma que en el registro se crea un evento al boton de login, se toman los datos se los pasao a unas variables y se llama a la función loginUser.</p></br>
<p align="center">
  <img src="/Capturas/loginDos.JPG" width="500">
</p>
<h3>loginUser</h3></br>
<p>Esta función realiza el inicio de sesión llamando al método SignInWithEmailAndPassword de objeto FirebaseAuth, acompañado de un evento donde preguntamos si dicha tarea se completo, pasamos a la ventana de perfil.</p></br>
<p align="center">
  <img src="/Capturas/loginTres.JPG" width="500">
</p>
<h1>Clase Contacto</h1></br>
<p>Esta es una sencilla clase que permitirá manejar los datos de los contactos que guardaremos en la agenda, como atributos tendra un id de generación aleatoria, nombre, correo, telefono, un constructor vacio, sus setters and getters y un toString.</p></br>
<p align="center">
  <img src="/Capturas/claseContacto.JPG" width="500">
</p>
<h1>menu_Main.xml</h1></br>
<p>En primer lugar debemos crear los iconos del menú. Esto lo hacemos facilmente dando click cobre res, Nuevo, Image Asset, se abre la ventana de configuración y seleccionamos el tipo de icono y el icono en si. En el mismo res se creo una carpeta de menu aqui incorporamos el menu_main. Este menú main contiene los 3 iconos que creamos (nuevo, guardar, eliminar) para cargarlos en la ventana de perfil.</p></br>
<p align="center">
  <img src="/Capturas/menu.JPG" width="500">
</p>
<h1>ProfileActivity</h1></br>
<p>En esta actividad manejamos nuestra agenda. A primera vista nos encontraremos con el menu creado segudio de el boton de cerrar sesión y el nombre de quien inicio sesión. Luego encontramos los EditText para agregar la información de los contactos. Finalmente se observa la lista de contactos en un ListView. A continuación se podrá ver el diseño final.</p></br>
<p align="center">
  <img src="/imagenes/imgPerfil.jpg" width="350">
</p>
<h2>Funciones</h2></br>
<h3>onCreate</h3></br>
<p>Aqui esta la instanciación de de los campos y objetos necesarios. Despues llamamos a la función listarDatos, luego a nuestro ListView llamado ContactsList le adjuntamos un evento para tomar los datos y pasarlo a un objeto Contacto. Después mediante un evneto manejamos el cierre de sesión, aqui llamamos al metodo signOut del objeto FirebaseAuth.</p></br>
<p align="center">
  <img src="/Capturas/profileUno.JPG" width="500">
</p>
<p align="center">
  <img src="/Capturas/profileDos.JPG" width="500">
</p>
<p align="center">
  <img src="/Capturas/profileTres.JPG" width="500">
</p>
<p align="center">
  <img src="/Capturas/profileCuatro.JPG" width="500">
</p>
<h3>listarDatos</h3></br>
<p>Mediante un evento sobre la colección accedida por el databaseReference, esto con el fin de capturar los hijos de la colección, recorrerlos uno por uno, crear un objeto Contacto y llevarlos al listView. Finalmente aqui creamos un array de Contactos.</p></br>
<p align="center">
  <img src="/Capturas/profileCinco.JPG" width="500">
</p>
<h3>onCreateOptionsMenu</h3></br>
<p>Traemos el menú creado anteriormente</p></br>
<p align="center">
  <img src="/Capturas/profileSeis.JPG" width="500">
</p>
<h3>onOptionsItemSelected</h3></br>
<p>Aqui nos basamos en el item del menu seleccionado para efectuar una acción. Se implemento un switch analizando el id de icono.</p></br>
<p>En el caso de el de agregar, se creará un objeto contacto, se setearan los datos al objeto y se envia dicho objeto con el databaseReference de Firebase.</p></br>
<p align="center">
  <img src="/Capturas/profileSiete.JPG" width="500">
</p>
<p>En el caso de Guardar, de igual forma se crea el objeto Contacto y se setean los datos, se los envia pero en este caso reemplaza por el id.</p></br>
<p>En el caso de Borrar, se crea un objeto, se le setea el id del contacto seleccionado, se pasa este id al databaseReference con el metod removeValue que eliminará el contacto en Firebase. Finalmente limpiamos los EditText.</p></br>
<p align="center">
  <img src="/Capturas/profileOcho.JPG" width="500">
</p>
<h3>getUserInfo</h3></br>
<p>Esta función permite traer el nombre del usuario que ha iniciado sesión. Como se emcionó anteriormente los datos se guardaron en una colección. Obtenemos el id del usuario loegeado, este id lo pasamos al databaseReference y obtenemos el nombre para luego mostrarlo en el TextView</p></br>
<p align="center">
  <img src="/Capturas/profileNueve.JPG" width="500">
</p>
