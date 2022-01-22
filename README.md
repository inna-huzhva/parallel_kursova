### Як збудувати проєкт

- встановити maven https://maven.apache.org/install.html
- виконати тести
```bash
mvn clean test
```
- виконати команду побудови з директорії проєкта
```bash
mvn clean package
```
- jar-файл з вбудованими залежностями буде збережений в target

### Як запустити програму

- виконати команду
```bash
java -jar target/kursova-1.0-jar-with-dependencies.jar %шлях-директорії-з-файлами-для-індексації% %кількість-потоків%
```
- в результаті буде запущений сервер, який обслуговує запити на порту 4567
- можна робити запити з браузера, наприклад, 'http://localhost:4567/search/dog', aбо 'http://localhost:4567/search/dog/and/food'
