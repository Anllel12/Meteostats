#include <WiFi.h>
#include <time.h>

// Configuración de la red Wi-Fi
const char* ssid = "Nombre_de_la_red";
const char* password = "Contraseña_de_la_red";

// Configuración de la hora de inicio
const char* ntpServer = "pool.ntp.org";
const long gmtOffset_sec = -18000;
const int daylightOffset_sec = 3600;

// Función que se ejecutará cada hora
void myTask(void *pvParameters) {
  // Ingrese aquí el código que desea ejecutar cada hora
}

void setup() {
  Serial.begin(115200);

  // Conexión a la red Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Conectando a la red Wi-Fi...");
  }
  Serial.println("Conectado a la red Wi-Fi");

  // Sincronización de la hora actual
  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);
  struct tm timeinfo;
  if(!getLocalTime(&timeinfo)){
    Serial.println("Error al obtener la hora");
    return;
  }
  Serial.println(&timeinfo, "%A, %B %d %Y %H:%M:%S");

  // Crear tarea programada que se ejecutará cada hora
  xTaskCreatePinnedToCore(
    myTask,   /* Función que se ejecutará */
    "myTask", /* Nombre de la tarea */
    10000,    /* Tamaño de la pila */
    NULL,     /* Parámetros de la tarea */
    1,        /* Prioridad de la tarea (1 es el valor más alto) */
    NULL,     /* Identificador de la tarea */
    0);       /* Núcleo de la CPU en el que se ejecutará la tarea (0 o 1) */
}

void loop() {
  // No es necesario agregar nada aquí
}
