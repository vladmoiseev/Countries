# Dishes API Application

Этот репозиторий содержит простое приложение REST API, которое предоставляет информацию о столице страны по ее названию.

## Оглавление
1. [Введение](#введение)
2. [Функционал](#функционал)
3. [Используемые технологии](#используемые-технологии)
4. [Начало работы](#начало-работы)
5. [Необходимые компоненты](#необходимые-компоненты)
6. [Установка](#установка)
7. [Использование](#использование)
8. [Конфигурация](#конфигурация)
9. [Вклад](#вклад)

## Введение
Это базовое приложение REST API, построенное с использованием фреймворка Spring Boot. Приложение позволяет пользователям получать информацию о столице по названию страны, делая HTTP-запросы к предопределенным конечным точкам.

## Функционал
- Получение информации о столице страны по ее названию.
- Хранение данных о странах в базе данных.

## Используемые технологии
- Spring Boot: Веб-фреймворк для создания REST API.
- Spring Data JPA: Фреймворк доступа к данным для взаимодействия с базой данных.
- H2 Database: Встроенная база данных для локальной разработки.

## Начало работы
### Необходимые компоненты
Убедитесь, что у вас установлены следующие компоненты:
- Java (версия 17 или выше)
- Gradle

## Установка
1. Клонируйте репозиторий:

    ```bash
    git clone https://github.com/vladmoiseev/Countries
    ```

2. Создайте проект:

    ```bash
    mvn clean install
    ```

3. Запустите приложение:

    ```bash
    java -jar target/countries-api-1.0.0.jar
    ```

Приложение будет запущено на `http://localhost:8080`.

## Использование

### Конечные точки.

- **Получить информацию о стране из базы данных:**.
  
  ```http
  GET /countries/name?name={название_страны}
  ```

- **Получить информацию о стране по указанному названию:**

  ```http
  GET /countries/name?name=Belarus
  ```

### Конфигурация

Приложение использует внешнее API для получения информации о блюдах.

```properties
# application.properties
```

## Вклад

Вклад приветствуется! Если вы обнаружили какие-либо проблемы или хотите предложить улучшения, не стесняйтесь открыть проблему или создать запрос на исправление.
