создаем файл и копируем туда мой сертификат
    mycertificate.pem

создаем файл и копируем туда приватный ключ
    privatesertificate.pem

командой:
    openssl pkcs12 -export -out certificate.pfx -inkey privatesertificate.pem -in mycertificate.pem 
создаем файл который потом spring использует.