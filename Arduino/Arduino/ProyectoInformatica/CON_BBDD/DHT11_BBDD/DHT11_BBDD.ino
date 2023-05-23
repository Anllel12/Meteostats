#include <MySQL_Generic.h>
#include <DHT.h>
#include <DHT_U.h>

#define DHT_SENSOR_PIN  21 // ESP32 pin GIOP21 connected to DHT11 sensor
#define DHT_SENSOR_TYPE DHT11

DHT dht_sensor(DHT_SENSOR_PIN, DHT_SENSOR_TYPE);

#define MYSQL_DEBUG_PORT      Serial

// Debug Level from 0 to 4
#define MYSQL_LOGLEVEL      1

#include <MySQL_Generic.h>

#define USING_HOST_NAME     false

#if USING_HOST_NAME
  // Optional using hostname, and Ethernet built-in DNS lookup
  char server[] = "your_account.ddns.net"; // change to your server's hostname/URL
#else
  IPAddress server(195, 235, 211, 197);
#endif

// IPAddress server(195, 235, 211, 197);

////// AJUSTES BBDD
uint16_t server_port = 3306;    //3306;

//char default_database[] = "pi2_bd_meteostats";           //"test_arduino";
char default_database[] = "primeteostats";
//char default_table[]    = "hello_arduino";          //"test_arduino";

//String default_value    = "Hello, Arduino!"; 

/////// INICIALIZAMOS QUERY
String INSERT_SQL = "";

MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;

/////// DATOS WIFI
char ssid[] = "A52s Alex";             // your network SSID (name)
char pass[] = "12345678";         // your network password

//char ssid[] = "wireless-uem";             // your network SSID (name)
//char pass[] = "";         // your network password

/////// DATOS BBDD
char user[]         = "pri_meteostats";              // MySQL user login username
char password[]     = "pri_meteostats";          // MySQL user login password
char tipoSensor[] = "TIPO_SENSOR";

unsigned long delayTime;

void setup() {
  Serial.begin(115200);
  dht_sensor.begin();
  WiFi.begin(ssid, pass);

  MYSQL_DISPLAY1("\nStarting Basic_Insert_ESP on", ARDUINO_BOARD);
  MYSQL_DISPLAY(MYSQL_MARIADB_GENERIC_VERSION);

  /////// CONFIGURACION WIFI
  MYSQL_DISPLAY1("Connecting to", ssid);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");
}

void runInsert()
{
  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);

  if (conn.connected())
  {
    MYSQL_DISPLAY(INSERT_SQL);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(INSERT_SQL.c_str()) )
    {
      MYSQL_DISPLAY("Insert error");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
}

void loop() {

  int id = 1;
  
    float humidity = dht_sensor.readHumidity();
    float temperature = dht_sensor.readTemperature();
   Serial.print("Humedad = ");
    Serial.print(humidity);
    Serial.print("Temperatura = ");
    Serial.print(temperature);

   MYSQL_DISPLAY("Connecting...");
        INSERT_SQL = "INSERT INTO pri_meteostats.sensores (id_sensor, tipo_sensor, fecha, lectura1, usuario) VALUES (0, 'DHT11_humedad', now(), '" + String(humidity, 3) + "', 1) ";
        Serial.println(INSERT_SQL);
        delay(2000);

  if ((true) && (conn.connected() || conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL))
  {
    runInsert();
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  MYSQL_DISPLAY("Connecting...");
        INSERT_SQL = "INSERT INTO pri_meteostats.sensores (id_sensor, tipo_sensor, fecha, lectura1, usuario) VALUES (0, 'DHT11_temp', now(), '" + String(temperature, 3) + "', 1) ";
        Serial.println(INSERT_SQL);
        delay(2000);

  if ((true) && (conn.connected() || conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL))
  {
    runInsert();
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }
  
  Serial.println("Data sent to MySQL server");

  conn.close();
   //delay de 30 minutos
  delay(1800000);
}
