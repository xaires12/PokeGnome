# PokeGnomeGO

# Opis
Każdego dnia użytkownik będzie mógł po naciśnięciu guzika w widoku GnomeBall wylosować krasnala. Zadaniem użytkownika będzie dotarcie do lokalizacji gnoma, którego nazwę otrzymał oraz zrobienie zdjęcia krasnala. Losowanie będzie odbywać się metodą “bez oddawania” - jeśli użytkownik wypełni zadanie, nie wylosuje ponownie już zdobytego krasnala. Koniec gry ma miejsce, kiedy użytkownik zrobi zdjęcia wszystkim krasnalom w bazie danych. Po odblokowaniu lokalizacji krasnala użytkownik będzie mógł obejrzeć jego zdjęcie w galerii oraz napisać na forum.

﻿# Technologie
Język programowania: Kotlin
Baza danych: MySQLite
Serwer: Flask
Interfejs: REST API

# Instalacja
  
 1. Należy pobrać serwer Flask z https://github.com/xaires12/Flask, zainstalować potrzebne bilbioteki z pliku requierements.txt i uruchomić go z wiersza poleceń za pomocą komendy "python pokegnome.py". Następnie należy skopiować adres adres publiczny serwera oraz wkleić go do pliku app/src/main/java/network/ApiClient.kt w miejscu BASE_URL. Oraz zezwolić dostęp z domeny w pliku app/src/main/res/xml/network_security_config.xml
![image](https://github.com/xaires12/PokeGnome/assets/162905349/97e124b1-868f-498b-b560-280cfb4c8ac6)

2. Następnie w programie Android studio uruchomić aplikację z urządzeniem podłączonym do komputera.
3. Aplikacja powinna działać poprawnie, w razie problemów można przebudować program.
4. Po uruchomieniu można się zarejestrować a następnie zalogować.
5. W widoku GnomeBall można wylosować swojego krasnala
6. Krasnal oznacza się na mapie
7. Następnie po zrobieniu zdjęcia, jeżeli użytkownik znajduje się w zasięgu 100 metrów od krasnala wizyta zalicza się pozytywnie i zdjęcie zapisuje się do galerii.
8. Gdy wizyta jest zarejestrowana, można dodać komentarz pod odwiedzonym gnomem.
9. W profilu możemy zobaczyć ranking wszystkich użytkowników, zdobyte osiągnięcia, oraz powinny wyświetlać się zdjęcia, jednak jest problem z biblioteką glide.

# Autorzy
Emilia Cyprych - Back-end - [GitHub](https://github.com/Emicypr)
Paulina Kołodziejczyk - Front-end - [GitHub](https://github.com/PK127001)
Monika Śliwowska - Interfejs - [GitHub](https://github.com/xaires12)
