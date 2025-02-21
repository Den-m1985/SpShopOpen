https://github.com/pryhmez/springsecuritytutorial.git

https://dev.to/pryhmez/implementing-spring-security-6-with-spring-boot-3-a-guide-to-oauth-and-jwt-with-nimbus-for-authentication-2lhf

Создание открытых и закрытых ключей для шифрования и дешифрования.
Теперь в каталоге ресурсов мы создаем папку с именем certs, а затем открываем наш терминал и переходим в этот каталог, выполнив эту команду.

cd src/main/resources/certs

Затем мы будем использовать OpenSSL для создания пары ключей RSA 
(это должно быть установлено по умолчанию для пользователей Mac, 
а также может быть установлено для других пользователей).
Создайте закрытый ключ (RSA):

    openssl genpkey -algorithm RSA -out private-key.pem

Эта команда генерирует закрытый ключ RSA и сохраняет его в файле Private-key.pem.

Извлеките открытый ключ из закрытого ключа, выполнив:

    openssl rsa -pubout -in private-key.pem -out public-key.pem

Затем конвертируйте его в соответствующий формат PCKS и замените старый.

    openssl pkcs8 -topk8 -inform PEM -outform PEM -in private-key.pem -out private-key.pem -nocrypt

Хорошо, если последний шаг прошел успешно.


https://github.com/Den-m1985/WebMarket.git
