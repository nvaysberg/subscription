# Общее описание проекта

Проект реализован в соответствии с ТЗ, размещенном в файле tech_spec_java_spring_final_v2.pdf .

Принятые в данном демонстрационном проекте упрощения изложены в секции Disclaimer в конце документа.

# Запуск проекта
Для запуска контейнеров Docker в корневой папке проекта выполните команду:

    docker compose up -d

# Администрирование
Для доступа к приложению Adminer (администрирование БД Postgres) откройте URL (если вы запускали проект на локальном ПК):

    http://localhost:8081

Данные для доступа к БД

| Field            | Value        |
|------------------|--------------|
| Движок           | PostgreSQL   |
| Сервер           | db           |
| Имя пользователя | WebRise      |
| Пароль           | wr_secret1   |
| База данных      | subscription |

# Общее описание API

Для доступа к API обратитесь к эндпойнту нужной функции по URL (если вы запускали проект на локальном ПК):

    http://localhost:8081/<URL функции>

## Параметры функций

Параметры функций указаны в их описаниях.
Параметры методов HTTP POST и PUT передаются в теле запроса в формате JSON.
Параметры методов HTTP GET и DELETE передаются в URI запроса.

## Перечень функций

Реализованы следующие функции API для работы с пользователями:

| Название                            | URL         | Метод  |
|-------------------------------------|-------------|--------|
| Создание пользователя               | /users      | POST   |
| Получение списка пользователей      | /users      | GET    |
| Получение информации о пользователе | /users/{id} | GET    |
| Обновление данных пользователя      | /users/{id} | PUT    |
| Удаление пользователя               | /users/{id} | DELETE |

Реализованы следующие функции API для работы с сервисами:

| Название                  | URL       | Метод |
|---------------------------|-----------|-------|
| Создание сервиса          | /services | POST  |
| Получение списка сервисов | /services | GET   |

Реализованы следующие функции API для работы с подписками:

| Название                        | URL                                | Метод  |
|---------------------------------|------------------------------------|--------|
| Добавление подписки             | /users/{id}/subscriptions          | POST   |
| Получение подписок пользователя | /users/{id}/subscriptions          | GET    |
| Удаление подписки               | /users/{id}/subscriptions/{sub_id} | DELETE |

Реализованы следующие функции API для получения статистической информации:

| Название                            | URL                | Метод |
|-------------------------------------|--------------------|-------|
| Получение ТОП-3 популярных подписок | /subscriptions/top | GET   |

## Результаты

В случае успешного выполнения, все функции возвращают статус 200. 
Формат возвращаемых результатов в этом случае представлен в описании конкретной функции.
Если не указано иначе, результаты представлены в формате JSON.

## Обработка исключений

В случае ошибки валидации запроса, все функции возвращают статус 400, а в теле - сообщение об ошибке в формате JSON:

| Параметр  | Описание                                                  |
|-----------|-----------------------------------------------------------|
| timestamp | Временная метка ответа сервера                            |
| status    | Статус запроса HTTP                                       |
| error     | Сообщение об ошибке, соответствующее статусу запроса HTTP |
| path      | URI запроса                                               |

В случае ошибки выполнения запроса, все функции возвращают статус 500, а в теле - сообщение об ошибке в формате JSON:

| Параметр | Описание            |
|----------|---------------------|
| message  | Сообщение об ошибке |

В случае если идентифицируемая каким-либо параметром функции сущность - пользователь, сервис, подписка - не найдена,
все функции возвращают статус 404.

# Описание функций API

## Создание пользователя

### Описание эндпойнта

| Field | Value  |
|-------|--------|
| URL   | /users |
| метод | POST   |

### Описание параметров

| Параметр  | Обязательный | Описание                               |
|-----------|--------------|----------------------------------------|
| email     | да           | Уникальный почтовый адрес пользователя |
| firstName | нет          | Имя пользователя                       |
| lastName  | нет          | Фамилия пользователя                   |

### Описание результатов

| Параметр      | Описание                                     |
|---------------|----------------------------------------------|
| uniqueId      | Уникальный идентификатор пользователя (UUID) |
| email         | Уникальный почтовый адрес пользователя       |
| firstName     | Имя пользователя                             |
| lastName      | Фамилия пользователя                         |

### Пример выполнения вызова

    curl -X "POST" -H "Content-type: application/json" -d '{"email": "a@b.e", "firstName": "Кукуку", "lastName": "Бебебе"}' "localhost:8080/users"

## Получение списка пользователей

### Описание эндпойнта

| Field       | Value  |
|-------------|--------|
| URL         | /users |
| метод       | GET    |

### Описание параметров

У вызова нет параметров.

### Описание результатов

Результаты выполнения запроса аналогичны описанным выше для функции Создание пользователя.
В случае успешного выполнения, функция возвращает список существующих пользователей.

### Пример выполнения вызова

     curl "localhost:8080/users"

## Получение информации о пользователе

### Описание эндпойнта

| Field       | Value       |
|-------------|-------------|
| URL         | /users/{id} |
| метод       | GET         |

### Описание параметров

| Параметр  | Обязательный | Описание                                     |
|-----------|--------------|----------------------------------------------|
| id        | да           | Уникальный идентификатор пользователя (UUID) |

### Описание результатов

Результаты выполнения запроса аналогичны описанным выше для функции Создание пользователя.

### Пример выполнения вызова

    curl "http://localhost:8080/users/5b33faa5-92b7-4c60-ac46-9c38e07604c7"

## Обновление данных пользователя

### Описание эндпойнта

| Field       | Value       |
|-------------|-------------|
| URL         | /users/{id} |
| метод       | PUT         |

### Описание параметров

Уникальный идентификатор пользователя передается в URI запроса, как это описано выше для функции 
Получение информации о пользователе.
Обновленные данные пользователя передаются в теле запроса, как это описано выше для функции Создание пользователя.

#### Особенности обработки параметров

Если обязательные поля данных пользователя не переданы в запросе, они остаются прежними.
Если необязательные поля данных пользователя не переданы в запросе, они обнуляются в записи пользователя в БД.

### Описание результатов

Результаты выполнения запроса аналогичны описанным выше для функции Создание пользователя.

### Пример выполнения вызова

    curl -X "PUT" -H "Content-type: application/json" -d '{"firstName": "Кукукух"}' "http://localhost:8080/users/5b33faa5-92b7-4c60-ac46-9c38e07604c7"

Данный запрос изменяет имя пользователя, обнуляет его фамилию и оставляет прежний почтовый адрес.

## Удаление пользователя

### Описание эндпойнта

| Field       | Value       |
|-------------|-------------|
| URL         | /users/{id} |
| метод       | DELETE      |

### Описание параметров

Уникальный идентификатор пользователя передается в URI запроса, как это описано выше для функции
Получение информации о пользователе.

### Описание результатов

Результаты выполнения запроса аналогичны описанным выше для функции Получение информации о пользователе.

### Пример выполнения вызова

    curl -X "DELETE" "http://localhost:8080/users/5b33faa5-92b7-4c60-ac46-9c38e07604c7"

## Создание сервиса

### Описание эндпойнта

| Field | Value     |
|-------|-----------|
| URL   | /services |
| метод | POST      |

### Описание параметров

| Параметр  | Обязательный | Описание                    |
|-----------|--------------|-----------------------------|
| name      | да           | Уникальное название сервиса |

### Описание результатов

| Параметр      | Описание                                |
|---------------|-----------------------------------------|
| uniqueId      | Уникальный идентификатор сервиса (UUID) |
| name          | Уникальное название сервиса             |

### Пример выполнения вызова

    curl -X "POST" -H "Content-type: application/json" -d '{"name": "YouTube Premium"}' "localhost:8080/services"

## Получение списка сервисов

### Описание эндпойнта

| Field       | Value     |
|-------------|-----------|
| URL         | /services |
| метод       | GET       |

### Описание параметров

У вызова нет параметров.

### Описание результатов

Результаты выполнения запроса аналогичны описанным выше для функции Создание сервиса.
В случае успешного выполнения, функция возвращает список существующих сервисов.

### Пример выполнения вызова

     curl "localhost:8080/services"

## Добавление подписки

### Описание эндпойнта

| Field | Value                     |
|-------|---------------------------|
| URL   | /users/{id}/subscriptions |
| метод | POST                      |

### Описание параметров

| Параметр | Расположение | Обязательный         | Описание                                     |
|----------|--------------|----------------------|----------------------------------------------|
| id       | URI          | да                   | Уникальный идентификатор пользователя (UUID) |
| uniqueId | тело (JSON)  | да                   | Уникальный идентификатор сервиса (UUID)      |

### Описание результатов

| Параметр | Описание                                                           |
|----------|--------------------------------------------------------------------|
| uniqueId | Уникальный идентификатор подписки (UUID)                           |
| user     | Запись БД для пользователя, соответствующего параметру id функции  |
| service  | Запись БД для сервиса, соответствующего параметру uniqueId функции |

### Пример выполнения вызова

    curl -X "POST" -H "Content-type: application/json" -d '{"uniqueId": "76fb78aa-11c9-4fe5-8339-0f7b82d4facd"}' "http://localhost:8080/users/a76135aa-0ae2-431a-ae0b-b4592a58abad/subscriptions"

## Получение подписок пользователя

### Описание эндпойнта

| Field       | Value                     |
|-------------|---------------------------|
| URL         | /users/{id}/subscriptions |
| метод       | GET                       |

### Описание параметров

| Параметр | Обязательный         | Описание                                     |
|----------|----------------------|----------------------------------------------|
| id       | да                   | Уникальный идентификатор пользователя (UUID) |

### Описание результатов

Результаты выполнения запроса аналогичны описанным выше для функции Добавление подписки.
В случае успешного выполнения, функция возвращает список существующих подписок.

### Пример выполнения вызова

      curl "localhost:8080/users/a76135aa-0ae2-431a-ae0b-b4592a58abad/subscriptions"

## Удаление подписки

### Описание эндпойнта

| Field       | Value                              |
|-------------|------------------------------------|
| URL         | /users/{id}/subscriptions/{sub_id} |
| метод       | DELETE                             |

### Описание параметров

| Параметр | Обязательный         | Описание                                     |
|----------|----------------------|----------------------------------------------|
| id       | да                   | Уникальный идентификатор пользователя (UUID) |
| sub_id   | да                   | Уникальный идентификатор подписки (UUID)     |

### Описание результатов

Функция не возвращает результатов.

### Пример выполнения вызова

    curl -X "DELETE" "localhost:8080/users/a76135aa-0ae2-431a-ae0b-b4592a58abad/subscriptions/1dde483d-e0da-418c-9289-52b0e43685cb"

## Получение ТОП-3 популярных подписок

### Описание эндпойнта

| Field | Value              |
|-------|--------------------|
| URL   | /subscriptions/top |
| метод | GET                |

### Описание параметров

У вызова нет параметров.

### Описание результатов

| Параметр         | Описание                                |
|------------------|-----------------------------------------|
| uniqueId         | Уникальный идентификатор сервиса (UUID) |
| name             | Уникальное название сервиса             |
| numSubscriptions | Количество подписок на сервис           |

Сервисы в списке отсортированы от максимального количества подписок к минимальному.

### Пример выполнения вызова

    curl "localhost:8080/subscriptions/top"

# Disclaimer

Ввиду ограничений времени на выполнение задачи, задача решена для формального соответствия ТЗ, на базовом уровне,
в частности:

- не проводилась оптимизация БД в части объема хранения:
  - не решался вопрос использования UUID как ID записи,
  - UUID хранится в текстовом виде для наглядности,
  - максимальные длины полей заданы произвольно,
  - не рассматривался вопрос масштабирования БД в части использования встроенного механизма реляций, и т.д.
- Сообщения об ошибке вызовов передаются вызывающей функции, как они представлены в объекте Exception.
- Не проведена оптимизация запросов:
  - Уникальный идентификатор пользователя не нужен при удалении подписки

и так далее.
