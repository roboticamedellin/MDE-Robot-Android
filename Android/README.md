# MDE Robot

## Requisitos:

- Instalar [Android Studio](https://developer.android.com/studio/index.html?hl=es-419).

## Primera Parte:

Abrir el main_activity.xml:

- Cambiar el coordinator layout por el relative layout.
- Agregar dos botones en la parte bottom:
	- No remover el ```TextView``` y agregarle un id.
	- Agregar un id y usar los atributos ```layout_alignParentEnd``` y ```layout_alignParentBottom```
	- Agregar un texto a los botones y referenciarlo en el string resources.
	- Hacer lo mismo para el segundo boton pero ubicarlo en la parte inferior izquierda.
- Cambiar colores y usar un margin.


Abrir el codigo java:

- Identificar el metodo ```onCreate``` y ver la relacion de la vista con ```setContentView```.
- Crear una variable con referencia al boton usando ```findViewById(R.id.cta_left)``` y lo mismo para el ```TextView```.
- Agregar un listener.
- pasar la variable del boton y del textview a global.
- crear un metodo ```void leftClicked()``` para mostrar un ```Toast```.
- llamar el metodo anterior en el click listener.
- hacer lo mismo para el boton derecho.
- correr el codigo y verificar en el telefono o en el emulador.
## Segunda Parte:

El segundo paso es instalar algunas dependencias en el build.gradle.

 ```groovy
// ----> BORRAR:
compile 'com.android.support.constraint:constraint-layout:1.0.2'

// Bluetooth
compile 'com.akexorcist:bluetoothspp:1.0.0'

// Joystic
compile 'com.jmedeisis:bugstick:0.2.2'

// ButterKnife
compile 'com.jakewharton:butterknife:8.5.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'
```

Abrir el MainActivity.java:
- Usar el ```Butterknife.bind(this)```
- Usar ```@BindView(R.id.id_name)``` para el textView.
- Usar ```@OnClick(R.id.id_name)``` para el metodo usado en cada boton.
- Borrar los ```setOnClickListener``` y los ```findViewById```.
- Borrar las variables globales de los botones.

## Tercera Parte:
- Entrar al repositorio y copiar ```BluetoothController``` en un paquete llamado bluetooth.
- Resolver los imports.
- Crear una Clase en Java que se llame ```BaseActivity``` y que extienda de ```AppCompatActivity```.
- Sobreescribir los metodos:
    - ```onCreate(Bundle savedInstanceState)```
    - ```onResume()```
    - ```onActivityResult(int requestCode, int resultCode, Intent data)```
- Crear un ```ProgressDialog``` y un metodo para invocarlo de la siguiente manera:

```java
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    // ...

    void showProgressDialog() {
        dialog = ProgressDialog.show(this, "", getString(R.string.please_wait), true);
    }

    public void hideProgressDialog() {
        if (null != dialog) dialog.hide();
    }
```

- Implementar el ```ConnectedListener```:

```java
public class BaseActivity extends AppCompatActivity implements BluetoothController.ConnectedListener{
    // ...
}
```

- Notar el error y agregar la sugerencia del editor para implementar los metodos:
    - ```void onDeviceConnected()```
    - ```void onDeviceDisconnected()```
    - ```void onDeviceConnectionFailed()```
    - ```void loadDeviceList()```
- Crear una variable de la clase ```BluetoothController``` y agregar lo siguiente en el ```onCreate``` y en ```loadDeviceList```:

```java
public class BaseActivity extends AppCompatActivity implements BluetoothController.ConnectedListener{
    // ...
    private BluetoothController bluetoothController;
    // ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothController = new BluetoothController(this);
        bluetoothController.checkBluetoothState(this);
        bluetoothController.setConnectedListener(this);

        showDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothController.isBluetoothConnected()) {
            hideProgressDialog();
        }
    }
    // ...
    @Override
    public void loadDeviceList() {
        Intent intent = new Intent(this, DeviceList.class);
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
    }
    // ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BluetoothState.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothController.connectToBluetoothDevice(data);
                } else {
                    finish();
                }
                break;
            case BluetoothState.REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothController.connectToBluetoothService();
                } else {
                    finish();
                }
                break;
        }
    }
}
```

- Heredar en ```MainActivity.java``` de ```BaseActivity``` y no de ```AppCompatActivity```.

## Cuarta Parte:

- Agregar el siguiente metodo en ```BaseActivity```:

```java
public class BaseActivity extends AppCompatActivity implements BluetoothController.ConnectedListener{
    // ...
    public void sendMessage(String message) {
            bluetoothController.sendMessage(message);
    }
    // ...
}
```

- En el ```MainActivity``` usar la comunicación por bluetooth:
```java
public class MainActivity extends BaseActivity {
    // ...
    @OnClick(R.id.cta_left)
    public void leftClicked() {
        sendMessage("c");
    }
}
```