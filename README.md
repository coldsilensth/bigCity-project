# bigCity-project
from QA

My name: Adeline

My surname: Korchubekova

ЗАДАЧА:
Реализовать сервис, который принимает и отвечает на HTTP запросы.

ФУНКЦИОНАЛ:
1. В случае успешной обработки сервис должен отвечать статусом 200,
в случае любой ошибки — статус 400.
2. Сохранение всех объектов в базе данных.
3. Запросы:
4. 
● GET /city/ — получение всех городов из базы;

● GET /city//street/ — получение всех улиц города;
(city_id — идентификатор города)

● POST /shop/ — создание магазина; Данный метод получает json c объектом магазина, в ответ
возвращает id созданной записи.

● GET /shop/?street=&city=&open=0/1 — получение списка магазинов;

i. Метод принимает параметры для фильтрации.
Параметры не обязательны. В случае отсутствия параметров выводятся все магазины,
если хоть
один параметр есть , то по нему выполняется фильтрация.

ii. Важно!: в объекте каждого магазина выводится название города и улицы, а не id
записей.

iii. Параметр open: 0 - закрыт, 1 - открыт. Данный статус определяется исходя из
параметров
“Время открытия», “Время закрытия” и текущего времени сервера.

ОБЪЕКТЫ:
Магазин:
● Название
● Город
● Улица
● Дом
● Время открытия
● Время закрытия
Город:
● Название
Улица:
● Название
● Город



ПОДГОТОВИТЕЛЬНЫЕ ДЕЙСТВИЯ:
1. Создать базу данных в PostgreSQL и в проекте изменить application.properties

spring.jpa.hibernate.ddl-auto=create

spring.datasource.url=jdbc:postgresql://localhost:5432/big_cities(моя база данных, вы пишите свое название)

spring.datasource.username=postgres (также корректируете под себя username and password)

spring.datasource.password=postgres

spring.main.allow-circular-references=true

spring.jpa.properties.hibernate.format_sql=true

server.port=1010

2. После запуска убедитесь что создались 3 таблицы: city, shop, street. если все верно, то все хорошо :D можете открыть postman и начать проверять методы.
3. При проверке методов в postman можете воспользоваться Via API(мой ментор так делал, но я не знаю правильно ли это)

for city: https://api.postman.com/collections/25575773-87749aec-5580-4d78-b50a-fccf8bd8f9bb?access_key=PMAT-01GXGMEYZZQDFRNNED94C8CEZR

for shop: https://api.postman.com/collections/25575773-bdab1569-2275-459a-8584-6ea78bf3d241?access_key=PMAT-01GXGMJCH8RWDNMXY1ZPQQE1X1

for street: https://api.postman.com/collections/25575773-140c337a-6c4a-4575-9181-a8574574d16e?access_key=PMAT-01GXGMK1DXZ6TCK6P0EBDF4Z1D

Если ключи не сработали, то вот подробные описания для методов и примерные параметры для postman:


1. для начала работы вам стоит создать город(newCity)
http://localhost:1010/cities/newCity - post запрос 

body: {
  "name": "Бишкек"
}


2. создать улицу(newStreetAddCity)
http://localhost:1010/cities/1/streets - post запрос. 1 - айди вашего города, туда автоматически добавляется созданный город

body: {
    "name": "lo"
}


3. создать магазин(create)
http://localhost:1010/shop/create - post запрос

body: {
    "cityId": 1,
    "streetId": 1,
    "numberHome": 17,
    "closingTime": "23:00:00",
    "openingTime": "10:00:00"
}


Дальше можно поиграться с разными дефолт-методами и фильтрацией, но сейчас пройдемся по заданному функционалу.

1. Нужно вытащить все города из базы. Осуществляется get запросом

http://localhost:1010/cities/allCity

2. Получить все улицы города

http://localhost:1010/cities/1/streetsAll - get запрос где 1 - id города в котором хотите получить список всех улицы 

3. Создание магазина есть выше :D

4. Получение списка магазинов через фильтрацию.
5. 
http://localhost:1010/shop/allShops - вытаскивет список всех магазинов

http://localhost:1010/shop/allShops?city=Москва - вытаскивает список всех магазинов в городе Москва

http://localhost:1010/shop/allShops?city=Москва&street=Ленина - вытаскивает список всех магазинов в городе Москва и на улице Ленина


когда мы делаем get запрос у нас показывается дополнительное поле open
он варьируется от реального времени и если вы дергаете роут в 12 часов ночи, а магазин закрывается в 11, то поле покажет false.

Надеюсь вам понравился небольшой проект и readme, до свидания! :)
