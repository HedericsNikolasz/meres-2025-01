<?php
header('Content-Type: application/json; charset=utf-8'); 

$servername = "localhost"; 
$username = "felhasználónév";
$password = "jelszó";
$dbname = "adatbázis_neve";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["error" => "Adatbázis hiba: " . $conn->connect_error]));
}

if (isset($_GET['nap'])) {
    $nap = $_GET['nap'];
    $sql = "SELECT * FROM nevnapok WHERE nap = '$nap'";
} elseif (isset($_GET['nev'])) {
    $nev = $_GET['nev'];
    $sql = "SELECT * FROM nevnapok WHERE nevnap1 LIKE '%$nev%' OR nevnap2 LIKE '%$nev%' LIMIT 1"; 
} else {
    die(json_encode(["error" => "Hiányzó paraméter"]));
}

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    echo json_encode($row);
} else {
    echo json_encode(["error" => "Nincs találat"]);
}

$conn->close();
?>