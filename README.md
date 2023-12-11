# Task-Management-System
## Инструкция для запуска приложения:
```
docker build -t application-image
```
```
docker-compose up .
```
## Реализованные функции:
порт приложения `localhost:8080`

- регистрация пользователя `POST` `/api/signUp`.
- авторизация пользователя `POS` `/api/login`.
- создание новой задачи `POST` `/api/tasks/create`. Автором является пользователь, создавший задачу. Default: Priority = MEDIUM, Status = AWAIT.
- получение списка задач автора/исполнителя `GET` `/api/tasks/byAuthor/{author_id/executor_id}?page={}&size={}`.
- редактирование задачи `PUT` `/api/tasks/{task_id}`.
При отправке запросов (кроме регистрации и авторизации) необходимо указывать JWT токен, выданный при авторизации.

Пример запроса:
```
PUT localhost:8080/api/tasks/create
Content-type: application/json
Authorization: Bearer {token}

{
  "title": "Task"
  "description": "Description"
  "comment": "Comment"
  "executor": 1 (id исполнителя)
  "Priority": "higt"
}
```
## Будущие изменения:
- покрытие тестами;
- обработка **всех** исключений (в данный момент из-за нехватки времени удалось обработать не все);
- интеграция *Swagger* и *Swagger UI* (из-за проблем с совместимостью jakarta и большинства общепринятых фреймворков для работы с *Swagger*, сейчас он не предусмотрен);
- разработка новых функций приложения.
