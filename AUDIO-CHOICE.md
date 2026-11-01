# Selección de audio para descargas

Versión modificada y no oficial basada en NewPipe v0.29.1 (commit
`00acf2f318b0af7ffacbd687bba011b642cc01d0`).

## Cambios

- Permite elegir una pista al descargar video cuando YouTube ofrece varios audios.
- Conserva formatos de video sin audio para combinarlos con la pista elegida.
- Reconoce el marcador `acont=dubbed-auto` en los metadatos y muestra el doblaje
  automático de YouTube por separado del doblaje normal.
- Conserva el identificador de pista al recuperar una descarga cuya URL caducó.
  Si no encuentra la misma pista, evita sustituirla por otro idioma.
- Añade etiquetas en español e inglés al diálogo de descarga.

La disponibilidad depende de las pistas que YouTube exponga al extractor.
La app no genera traducciones ni doblajes.

## Identidad de la app

La variante debug usa el nombre `NewPipe Audio`, el paquete
`org.schabi.newpipe.audiochoice` y la versión `0.29.1-audio-choice`, por lo que
puede coexistir con la app oficial. La variante release mantiene la configuración
original del proyecto; el APK entregado se construyó con `assembleDebug`.

## Compilar y validar

Se compiló con JDK 21, Gradle 9.6.1 y el SDK de Android configurado en el proyecto.
Configurar `sdk.dir` en un archivo local `local.properties` y ejecutar:

```sh
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:runCheckstyle
```

El APK se genera en `app/build/outputs/apk/debug/`.
La compilación entregada pasó 162 pruebas unitarias y Checkstyle.
La comprobación en dispositivos y videos concretos debe hacerse por separado.

## Actualizaciones

La rama `audio-choice` conserva los cambios sobre la etiqueta oficial v0.29.1.
El remoto `upstream` debe apuntar a `https://github.com/TeamNewPipe/NewPipe.git`.
Al incorporar una nueva versión oficial, crear primero una rama de trabajo,
fusionar la etiqueta nueva, resolver los conflictos y compilar y verificar otra vez.
Si el clon es superficial, descargar el historial completo antes de fusionar.

Para que Android acepte una actualización sobre el APK ya instalado, conservar
el identificador de paquete y la clave de firma, e incrementar `versionCode`.
El APK inicial usa una clave de depuración: para distribución pública se debe
decidir la estrategia de firma. Las claves privadas no deben publicarse.

## Licencia y procedencia

Este código deriva de TeamNewPipe/NewPipe y conserva los avisos y la licencia
GNU GPL v3 o posterior del proyecto original. Consultar `LICENSE`.
Al distribuir un APK, proporcionar el código fuente correspondiente.
No es una versión oficial ni implica respaldo de Team NewPipe.
