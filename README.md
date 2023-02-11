# Сервис выдачи заказов

## Unit

**GET /api/units** - получение списка всех единиц измерения

**GET /api/units/{shortName}** - получение единицы измерения по сокращённому наименованию

**POST /api/units** - добавление единицы измерения

---

## Product

**GET /api/goods** - получение списка всех товаров

**GET /api/goods/{id}** - получение товара по идентификатору

**GET /api/goods/find?query={query}** - получение списка товаров по содержанию наименования в запросе

**POST /api/goods** - добавление товара

**PUT /api/goods/image/{id}** - обновление изображения у товара

**PUT /api/goods/{id}** - обновление данных товара

**DELETE /api/goods/{id}** - удаление товара по идентификатору

---

## Basket

**GET /api/baskets/{orderId}** - получение корзины покупок по идентификатору заказа

---

## Order

**GET /api/orders** - получение списка всех заказов

**GET /api/orders/{id}** - получение заказа по идентификатору

**POST /api/orders** - добавление заказа

**DELETE /api/orders/{id}** - удаление заказа по идентификатору

**PUT /api/orders** - изменение статуса заказа