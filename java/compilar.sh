javac escritorio.java
echo "Main-Class: escritorio">manifesto.txt
jar cfm escritorio.jar manifesto.txt escritorio.class escritorio.java inicio.png programa.png imagen.png texto.txt audio.wav video.mp4 index.html sistema/* sistema/programas/* sistema/juegos/*
java -Dswing.metalTheme=steel -jar escritorio.jar
