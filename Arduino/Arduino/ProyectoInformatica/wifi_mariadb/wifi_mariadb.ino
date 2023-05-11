#include <WiFi.h>
#include <MySQL_Connection.h>
#include <MySQL_Cursor.h>

// WiFi credentials
const char ssid[] = "UE-Students";
const char password[] = "contraseña_de_tu_red_wifi";

// MySQL server credentials
IPAddress server_addr(192, 168, 1, 100); // Dirección IP del servidor de la base de datos
const char user[] = "nombre_de_usuario";
const char password_db[] = "contraseña_de_usuario";
const char database[] = "nombre_de_la_base_de_datos";

// Create WiFi client and MySQL objects
WiFiClient client;
MySQL_Connection conn((Client *)&client);

void setup() {
  Serial.begin(115200);

  // Connect to WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");

  // Connect to MySQL server
  if (conn.connect(server_addr, 3306, user, password_db, database)) {
    Serial.println("Connected to MySQL server");
  } else {
    Serial.println("Failed to connect to MySQL server");
    return;
  }

  // Send SQL query
  MySQL_Cursor cursor = new MySQL_Cursor(&conn);
  cursor->execute("INSERT INTO my_table (column1, column2) VALUES (1, 'hello')");
  delete cursor;

  // Disconnect from MySQL server
  conn.close();
}

void loop() {
  // Do nothing
}