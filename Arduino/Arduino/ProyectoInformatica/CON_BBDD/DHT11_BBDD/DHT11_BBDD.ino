#include <DHT.h>
#include <ESP8266WiFi.h>
#include <MySQL_Connection.h>
#include <MySQL_Cursor.h>

#define DHTPIN D3
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

#define MYSQL_DEBUG_PORT      Serial

// Debug Level from 0 to 4
#define MYSQL_LOGLEVEL      1

#include <MySQL_Generic.h>

#define USING_HOST_NAME     false

#if USING_HOST_NAME
  // Optional using hostname, and Ethernet built-in DNS lookup
  char server[] = "your_account.ddns.net"; // change to your server's hostname/URL
#else
  IPAddress server(192, 168, 61, 78);
#endif

// IPAddress server(195, 235, 211, 197);

////// AJUSTES BBDD
uint16_t server_port = 3306;    //3306;

//char default_database[] = "pi2_bd_meteostats";           //"test_arduino";
char default_database[] = "pi2_bd_meteostats";
char default_table[]    = "hello_arduino";          //"test_arduino";

String default_value    = "Hello, Arduino!"; 

/////// INICIALIZAMOS QUERY
String INSERT_SQL = "";

MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;

/////// DATOS WIFI
char ssid[] = "juanki";             // your network SSID (name)
char pass[] = "12345";         // your network password

//char ssid[] = "wireless-uem";             // your network SSID (name)
//char pass[] = "";         // your network password

/////// DATOS BBDD
char user[]         = "pi2_meteostats";              // MySQL user login username
char password[]     = "pi2_meteostats";          // MySQL user login password
char tipoSensor[] = "TIPO_SENSOR";

unsigned long delayTime;

void setup() {
  Serial.begin(115200);
  dht.begin();
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

  if(true){
    float humidity = dht.readHumidity();
  //float temperature = dht.readTemperature();
   Serial.print("Humedad = ");
    Serial.print(humidity);


    if (!conn.connected()) {
    Serial.println("Connecting to MySQL server...");
    if (conn.connect(server_addr, 3306, user, password, database)) {
      Serial.println("Connected to MySQL server");
    } else {
      Serial.println("Connection failed");
      return;
    }
  }

   MYSQL_DISPLAY("Connecting...");
        INSERT_SQL = "INSERT INTO pi2_bd_meteostats.sensores (id_sensor, tipo_sensor, fecha, lectura1, usuario) VALUES (0, '"+String(tipoSensor)+"', now(), '" + String(humidity, 3) + "', 1) ";
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
  }
  


  Serial.println("Data sent to MySQL server");

  conn.close();
  delay(60000); // Esperar un minuto antes de enviar la siguiente lectura
}
