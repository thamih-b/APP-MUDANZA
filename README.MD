há uma maneira de copiar o conteudo do codigo do android studio (otter) para um bloco de notas ou algo assim para que voce possa ler sem eu precisar copiar e colar cada classe?

Sim, há maneiras simples e eficientes de exportar todo o código do seu projeto no Android Studio (Otter) para um único arquivo de texto, sem copiar classe por classe. Isso é ideal para compartilhar comigo ou analisar em um bloco de notas.
Método Principal: Terminal do Android Studio

Abra o Terminal no Android Studio (aba inferior) e execute este comando na raiz do projeto. Ele encontra e concatena todos os arquivos Kotlin (.kt), Java (.java) e XML relevantes em um arquivo FullProjectCodeBackup.txt.

text
find . -name "*.java" -o -name "*.kt" -o -name "*.xml" | while read file; do
  echo -e "\n\n====================== FILE: $file ======================\n" >> FullProjectCodeBackup.txt
  cat "$file" >> FullProjectCodeBackup.txt
done

O resultado inclui cabeçalhos com caminhos dos arquivos para facilitar a leitura, como ====================== FILE: ./app/src/main/java/... ====================== seguido do conteúdo.
Opção Extra: Ferramenta Online

Use o Project2Txt (project2txt.com), que converte projetos inteiros (incluindo do Android Studio/VSCode) em um TXT único. Faça upload da pasta do projeto ou use extensões para IDEs.
Dicas para Seu TCC

Como você trabalha com Kotlin, MySQL e app de pet care, adicione -o -name "*.gradle" -o -name "*.json" ao find para incluir configs. Abra o TXT gerado e cole aqui para eu analisar erros ou sugerir otimizações no Jetpack Compose.
PS C:\\Users\\thami\\AndroidStudioProjects\\APP-MUDANZA> find . -name "*.java" -o -name "*.kt" -o -name "*.xml" -o -name "*.gradle" -o -name "*.json" | while read file; do
>>   echo -e "\\\\n\\\\n====================== FILE: $file ======================\\\\n" >> FullProjectCodeBackup.txt
En línea: 1 Carácter: 115
+ ... e "*.xml" -o -name "*.gradle" -o -name "*.json" | while read file; do
+                                                                          ~
Falta el cuerpo de instrucciones en el bucle do.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : MissingLoopStatement
 
PS C:\\Users\\thami\\AndroidStudioProjects\\APP-MUDANZA>   cat "$file" >> FullProjectCodeBackup.txt
Get-Content : No se puede enlazar el argumento con el parámetro 'Path' porque es una cadena vacía.
En línea: 1 Carácter: 7
+   cat "$file" >> FullProjectCodeBackup.txt
+       ~~~~~~~
    + CategoryInfo          : InvalidData: (:) [Get-Content], ParameterBindingValidationException
    + FullyQualifiedErrorId : ParameterArgumentValidationErrorEmptyStringNotAllowed,Microsoft.PowerShell.Commands.GetContentCommand
 
PS C:\\Users\\thami\\AndroidStudioProjects\\APP-MUDANZA> done
done : El término 'done' no se reconoce como nombre de un cmdlet, función, archivo de script o programa ejecutable. Compruebe si escribió correctamente el nombre o, si incluyó 
una ruta de acceso, compruebe que dicha ruta es correcta e inténtelo de nuevo.
En línea: 1 Carácter: 1
+ done
+ ~~~~
    + CategoryInfo          : ObjectNotFound: (done:String) [], CommandNotFoundException
    + FullyQualifiedErrorId : CommandNotFoundException

O erro acontece porque você está no PowerShell do Windows (PS), que usa sintaxe diferente do bash/Linux que eu sugeri. Vamos usar comandos nativos do PowerShell para gerar o arquivo TXT com todo o código.
Comando Correto para PowerShell

Cole este script completo no Terminal do Android Studio (ou PowerShell na pasta do projeto). Ele lista arquivos Kotlin/Java/XML/Gradle/JSON e os concatena em FullProjectCodeBackup.txt com cabeçalhos.

text
Get-ChildItem -Recurse -Include *.kt,*.java,*.xml,*.gradle,*.json | ForEach-Object {
    "`n`n====================== FILE: $($_.FullName) ======================`n" | Add-Content -Path FullProjectCodeBackup.txt -Encoding UTF8
    Get-Content $_.FullName | Add-Content -Path FullProjectCodeBackup.txt -Encoding UTF8
}

Execute linha por linha ou como bloco (selecione tudo e Ctrl+Shift+P > "Run Selection in Terminal"). O arquivo será criado na raiz do projeto.
Verificação e Uso

Após rodar, abra FullProjectCodeBackup.txt no Bloco de Notas ou VS Code (cerca de 1-10MB dependendo do projeto). Cole o conteúdo aqui para eu debugar seu app de pet care (Kotlin/Jetpack Compose).

Isso resolve para seu TCC sem Git Bash ou WSL.
PS C:\\Users\\thami\\AndroidStudioProjects\\APP-MUDANZA> Get-ChildItem -Recurse -Include *.kt,*.java,*.xml,*.gradle,*.json | ForEach-Object {
>>     "`n`n====================== FILE: $($_.FullName) ======================`n" | Add-Content -Path FullProjectCodeBackup.txt -Encoding UTF8                                      
>>     Get-Content $_.FullName | Add-Content -Path FullProjectCodeBackup.txt -Encoding UTF8                                                                                         
>> }
Get-Content : Acceso denegado a la ruta de acceso 'C:\\Users\\thami\\AndroidStudioProjects\\APP-MUDANZA\\.gradle'.
En línea: 3 Carácter: 5
+     Get-Content $_.FullName | Add-Content -Path FullProjectCodeBackup ...
+     ~~~~~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : PermissionDenied: (C:\\Users\\thami\\...MUDANZA\\.gradle:String) [Get-Content], UnauthorizedAccessException
    + FullyQualifiedErrorId : GetContentReaderUnauthorizedAccessError,Microsoft.PowerShell.Commands.GetContentCommand

O erro "Acceso denegado" ocorre porque o PowerShell tenta acessar a pasta .gradle, que tem restrições de permissão no Windows. A solução adiciona -ErrorAction SilentlyContinue para pular erros sem parar o script.
Comando Corrigido para PowerShell

Cole este no Terminal do Android Studio. Ele ignora pastas protegidas e gera o FullProjectCodeBackup.txt só com arquivos acessíveis (Kotlin, Java, XML, etc.).

text
Get-ChildItem -Recurse -Include *.kt,*.java,*.xml,*.gradle,*.json -ErrorAction SilentlyContinue | ForEach-Object {
    "`n`n====================== FILE: $($_.FullName) ======================`n" | Add-Content -Path FullProjectCodeBackup.txt -Encoding UTF8
    try { Get-Content $_.FullName -ErrorAction SilentlyContinue | Add-Content -Path FullProjectCodeBackup.txt -Encoding UTF8 } catch {}
}

Rode e verifique o TXT (abra no Notepad++ para sintaxe). Vai capturar 99% do seu código do TCC sem problemas.
Se Ainda Der Erro

Exclua .gradle e build explicitamente com filtro:

text
| Where-Object { $_.FullName -notlike '*\.gradle\*' -and $_.FullName -notlike '*build\*' }

Adicione após -ErrorAction SilentlyContinue. Agora cole o backup aqui para eu revisar o app de pet care!
MudanzApp. Direito: Thamiris Bittencourt, Enrique Martínez y Miguel Martínez. 2026. O uso deve ser apenas pessoal ou para estudos, nenhuma comercializacao ou distribuicao é autorizada sem o consentimento expresso dos autores. 
é para portfolio.

Perfeito — para o teu caso, o mais correto é um LICENSE proprietário / não comercial, não uma licença open source como MIT. Em repositórios públicos, isso normalmente significa deixar claro que o código pode ser visto para portfólio, mas não pode ser reutilizado, distribuído nem explorado comercialmente sem permissão expressa dos autores.
O que vou considerar

Vou montar um texto de licença com estas regras:

    nome do projeto: MudanzApp.

    autores/direitos: Thamiris Bittencourt, Enrique Martínez y Miguel Martínez.

    ano: 2026.

    uso permitido: apenas pessoal e para estudos.

    proibido: comercialização, redistribuição e derivados sem consentimento expresso.

    finalidade: portfólio.

Observação importante

Isso não é uma licença aberta padrão, então o arquivo LICENSE será um texto personalizado, parecido com um aviso de copyright com restrições claras. GitHub permite que você adicione um arquivo de licença, e também recomenda que ele fique na raiz do repositório para ficar visível.
Texto sugerido para o LICENSE

Você pode usar este conteúdo em LICENSE:

text
Copyright (c) 2026 Thamiris Bittencourt, Enrique Martínez y Miguel Martínez

MudanzApp

Todos os direitos reservados.

É concedida permissão para visualizar, copiar e usar este código exclusivamente para fins pessoais, acadêmicos e de estudo.

Qualquer forma de comercialização, redistribuição, publicação, sublicenciamento ou criação de obras derivadas com base neste código, no todo ou em parte, é estritamente proibida sem o consentimento expresso e por escrito dos autores.

Este software é fornecido "tal como está", sem garantias de qualquer tipo, expressas ou implícitas.

Para solicitações de permissão, entre em contato com os autores.

Versão em inglês, se quiser manter padrão de repositório

Se preferires algo mais internacional para o GitHub, posso adaptar para inglês e deixar com aparência mais profissional de portfólio.
Recomendação para o README

No README, vale colocar uma frase curta como:
“This project is shared for portfolio and educational purposes only. No commercial use or redistribution is allowed without explicit permission from the authors.”

Se quiser, eu posso agora te entregar:

    uma versão em português,

    uma versão em inglês, e

    uma versão mais formal jurídica,
    todas prontas para copiar e colar no arquivo LICENSE.

versao em ingles e castellano

Claro — aqui estão as duas versões prontas para colar no arquivo LICENSE.
English version

text
Copyright (c) 2026 Thamiris Bittencourt, Enrique Martínez, and Miguel Martínez

MudanzApp

All rights reserved.

Permission is granted to view, copy, and use this code exclusively for personal, academic, and educational purposes.

Any form of commercialization, redistribution, publication, sublicensing, or creation of derivative works based on this code, in whole or in part, is strictly prohibited without the express written consent of the authors.

This software is provided "as is", without warranty of any kind, express or implied.

For permission requests, please contact the authors.

Castellano version

text
Copyright (c) 2026 Thamiris Bittencourt, Enrique Martínez y Miguel Martínez

MudanzApp

Todos los derechos reservados.

Se concede permiso para ver, copiar y utilizar este código exclusivamente con fines personales, académicos y educativos.

Cualquier forma de comercialización, redistribución, publicación, sublicencia o creación de obras derivadas basadas en este código, en su totalidad o en parte, está estrictamente prohibida sin el consentimiento expreso y por escrito de los autores.

Este software se proporciona "tal como está", sin garantía de ningún tipo, expresa o implícita.

Para solicitudes de permiso, póngase en contacto con los autores.

Observação

Para portfólio, eu recomendo usar a versão em inglês no LICENSE e, se quiser, colocar a versão em espanhol no README ou em um arquivo NOTICE. Isso deixa o repositório mais padrão e mais fácil de entender por recrutadores.

Se quiser, eu posso montar agora um README curto combinando com esse LICENSE.
Melhore o readme 

¡Bienvenidos al proyecto APP-MUDANZA! Esta es la aplicación móvil para Android desarrollada como Trabajo de Conclusão de Curso (TCC) por nuestro equipo de 5 estudiantes de FP en Informática.

La app está diseñada para gestionar servicios de mudanzas (mudanzas de muebles, enseres y pequeños traslados), conectando clientes con proveedores locales. Usa Kotlin, Jetpack Compose para UI moderna, MySQL como base de datos backend, y arquitectura MVVM con Room para datos locales (o Retrofit para API).
​

Objetivos principales:

Registro/login de usuarios (clientes y proveedores).

Listado de servicios disponibles con filtros.

Solicitud de presupuestos y chat en tiempo real.

Gestión de citas y seguimiento de mudanzas.

Panel admin para moderación.

El proyecto está en fase final: funcional en emulador/dispositivo, pero necesita pruebas exhaustivas y polish UI.

Requisitos para Desarrollar
Android Studio Otter (o superior, 2024.2+).

JDK 17 o superior.

Emulador API 34+ o dispositivo físico.

MySQL 8.0 local (XAMPP/WAMP) para DB de desarrollo.

Git para control de versiones.

Dependencias clave (en build.gradle): Compose 1.6+, Lifecycle 2.8+, Navigation 2.8+, Hilt 2.52, Retrofit 2.11, Room 2.7+.
​

Instalación rápida:

Clona el repo: git clone [URL].

Abre en Android Studio: File > Open > carpeta app.

Sync Gradle (botón elefante).

Configura DB (ver sección Base de Datos).

Run en emulador.

Estructura de Módulos
El proyecto sigue Multi-Módulo para escalabilidad. Carpeta principal: app/ contiene todo.

app/
├── src/main/java/com/[paquete]/appmudanza/     # Código Kotlin principal
│   ├── di/                 # Inyección de dependencias (Hilt modules)
│   ├── data/               # Repositorios, DTOs, API calls
│   │   ├── local/          # Room DB entities/DAOs
│   │   └── remote/         # Retrofit services
│   ├── domain/             # UseCases, Models de negocio
│   ├── ui/                 # Pantallas Compose (Screens/)
│   │   ├── auth/           # Login/Register
│   │   ├── home/           # Dashboard principal
│   │   ├── services/       # Listado y detalles de mudanzas
│   │   ├── profile/        # Perfil usuario
│   │   └── admin/          # Panel admin
│   ├── navigation/         # NavHost y rutas
│   └── MainActivity.kt     # Entry point con ComposeView
├── res/                    # Recursos XML (drawables, strings multi-idioma)
├── build.gradle.kts        # Dependencias app-level
└── proguard-rules.pro      # Optimización release

Paquetes clave identificados:

com.[paquete].appmudanza.ui.*: Todas las pantallas en Jetpack Compose (ej. HomeScreen, ServiceDetailScreen).
​

data.local: Entities como User, Service, Appointment (probablemente).
​

No hay README existente ni docs detalladas; este es el primero completo.
​

Arquitectura de la App (MVVM + Clean Architecture)
UI Layer: Jetpack Compose puro (no XML legacy). Usa ViewModels con StateFlow para estado reactivo.

Domain Layer: UseCases puros (ej. GetServicesUseCase, LoginUseCase).

Data Layer: Repositorios que combinan local (Room) + remote (Retrofit/MySQL via API PHP?).

Navegación: Navigation Compose con NavHost en MainActivity.

Inyección: Hilt (@HiltViewModel, @Singleton).

Tema: Material 3 con colores dinámicos (dark/light).
​

Flujo típico:

Splash -> Auth (si no logueado).

Home: Tabs (Servicios, Mis Mudanzas, Perfil).

Servicio: Lista paginada -> Detalle -> Solicitar Presupuesto.

Base de Datos (MySQL + Room)
Backend Principal: MySQL 8.0 (servidor local/remoto).

Host: localhost (dev) / [IP servidor prod].

Puerto: 3306.

DB Name: mudanza_db.

User/Pass: root / [password] (cambia en local.properties o Hilt module).

Script de creación (ejecuta en phpMyAdmin/MySQL Workbench):

CREATE DATABASE mudanza_db;
USE mudanza_db;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
email VARCHAR(150) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,
role ENUM('cliente', 'proveedor', 'admin') DEFAULT 'cliente',
phone VARCHAR(20),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE services (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(200) NOT NULL,
description TEXT,
price DECIMAL(10,2),
provider_id INT,
location VARCHAR(200),
FOREIGN KEY (provider_id) REFERENCES users(id),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
id INT AUTO_INCREMENT PRIMARY KEY,
client_id INT,
service_id INT,
date DATETIME NOT NULL,
status ENUM('pendiente', 'confirmada', 'completada', 'cancelada'),
notes TEXT,
FOREIGN KEY (client_id) REFERENCES users(id),
FOREIGN KEY (service_id) REFERENCES services(id)
);

-- Índices para performance
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_service_provider ON services(provider_id);

Integración en App:

Room DB Local: Cache offline (entities mirror de tablas MySQL).

@Database(entities = [User::class, Service::class, Appointment::class], version = 1)

Remote: Retrofit API (probablemente PHP backend en /api/ endpoints como /login, /services).

Sync: UseCase sincroniza local/remote on app start.
​

Config DB en código:

En DatabaseModule.kt (Hilt): return Room.databaseBuilder(...).build().

API Base URL: En BuildConfig.API_URL o secrets.

Cómo Compilar y Probar
Sync y Build: Gradle Sync > Build > Make Project.

Run: Selecciona emulador > Run 'app'.

Debug: Logs en Logcat (filtra "APP-MUDANZA").

Tests: Unitarios en test/ (JUnit), UI en androidTest/ (Compose UI Test).

Release: ./gradlew assembleRelease (firma con keystore).

Errores comunes:

DB conexión: Verifica MySQL running y credenciales.

Compose Preview: "Restart Preview" si falla.

Gradle: Limpia ./gradlew clean si sync falla.
​

Contribuciones del Equipo

Enrique Martínez y Miguel Martínez: Arquitectura Android, Compose UI principal
Thamiris Bittencourt (Thami B): Arquitectura Android, DB design, Compose UI general.


Commits: Siempre feat:, fix:, docs: en Git.


📄 Licencia

Propietarios / Todos los Derechos Reservados © 2026 APP-MUDANZA Todos los derechos reservados.

Prohibida la reproducción, distribución, modificación o uso comercial sin autorización expresa por escrito de los autores.

✅ Uso personal y educativo permitido únicamente para estudio. 🤝

🛠 Desarrollado con ❤️ para organización productiva. Última actualización: Marzo 2026
README_MudanzApp
MudanzApp

Mobile Android application developed as a final academic project focused on the management of moving services, vehicle registration, rentals, and incident reporting. The project is built with Kotlin and Jetpack Compose, follows an MVVM architecture, and uses Room as its local persistence layer.
Overview

MudanzApp was created to simplify the organization of moving-related services through a single mobile application. The app centralizes user authentication, move creation, vehicle management, rentals, and incident reporting in a modular structure designed for Android devices.

The technical implementation described in the project documentation uses Android Studio, Kotlin, Jetpack Compose, Navigation Compose, Room Database, and an MVVM-based architecture with reactive state management.
Main features

    User registration and login.

    Home screen with access to the main application modules.

    Move creation with vehicle assignment.

    Vehicle registration for vehicles with and without driver.

    Vehicle rental module with filtering by capacity.

    Vehicle profile view with ratings and comments.

    Incident reporting linked to vehicles and services.

    Local offline persistence using Room Database.

Tech stack
Area	Technology
Language	Kotlin
UI	Jetpack Compose
Architecture	MVVM
Navigation	Navigation Compose
Local database	Room Database
State management	StateFlow and collectAsState
IDE	Android Studio
Version control	Git and GitHub
Minimum Android API	26
Target API	34
Java version	JDK 17+
Project status

The application reached a functional academic stage with implemented modules for authentication, move management, vehicle registration, rentals, and incident reporting. The documentation also indicates that the project required final testing, validation, and interface polishing before broader distribution.
Application structure

The codebase is organized around a typical Android separation of concerns, with UI, ViewModel, and data responsibilities clearly split as part of the MVVM design described in the project memory.

text
app/
├── src/main/java/com/[package]/appmudanza/
│   ├── data/
│   │   ├── local/
│   │   └── remote/
│   ├── domain/
│   ├── navigation/
│   ├── ui/
│   │   ├── auth/
│   │   ├── home/
│   │   ├── moves/
│   │   ├── rentals/
│   │   ├── vehicles/
│   │   └── incidents/
│   └── MainActivity.kt
├── res/
├── build.gradle.kts
└── proguard-rules.pro

Core modules
Authentication

The authentication module allows users to register with name, email, and password, and then log in with their credentials. After a successful login, the user is redirected to the Home screen.
Move management

The move management module supports creating a move with origin, destination, client, date, and an assigned vehicle with driver. It also includes a view for registered moves and filtering by vehicle capacity.
Vehicle registration

The app supports the registration of vehicles with driver and vehicles without driver through separate forms. Registered vehicles become available to the modules that depend on them, such as move management and rental screens.
Vehicle rental

The rental module displays available vehicles without driver, allows filtering by load capacity, and lets the user select a vehicle and consult its profile information.
Incident reporting

The incident module lets users select a related vehicle and submit a description of a problem encountered during a service. Incidents are stored in the local database and confirmed through the interface.
Requirements

    Android Studio Otter or newer.

    JDK 17 or newer.

    Android emulator with API 34 or a physical Android device.

    Gradle configured with Kotlin DSL.

Local setup

    Clone the repository.

    Open the project in Android Studio.

    Let Gradle sync all dependencies.

    Run the app on an emulator or physical device.

    Test the main flows: authentication, moves, rentals, vehicles, and incidents.

Architecture notes

The implementation is based on MVVM, with Jetpack Compose used for the interface layer, ViewModels for state and logic orchestration, and Room entities, DAOs, and repositories for persistence. The documentation also describes centralized navigation with Navigation Compose and reactive UI updates through StateFlow and collectAsState.
Portfolio note

This repository is presented as an academic and portfolio project. It showcases Android development skills in Kotlin, local data persistence, modular screen design, and mobile application architecture for a logistics-related use case.
License

This project is shared for personal, academic, and portfolio viewing purposes only. Commercial use, redistribution, publication, sublicensing, or derivative works are not allowed without the express written consent of the authors.

Copyright (c) 2026 Thamiris Bittencourt, Enrique Martínez, and Miguel Martínez.
MudanzApp

Mobile Android application developed as a final academic project focused on the management of moving services, vehicle registration, rentals, and incident reporting. The project is built with Kotlin and Jetpack Compose, follows an MVVM architecture, and uses Room as its local persistence layer.
Overview

MudanzApp was created to simplify the organization of moving-related services through a single mobile application. The app centralizes user authentication, move creation, vehicle management, rentals, and incident reporting in a modular structure designed for Android devices.

The technical implementation described in the project documentation uses Android Studio, Kotlin, Jetpack Compose, Navigation Compose, Room Database, and an MVVM-based architecture with reactive state management.
Main features

    User registration and login.

    Home screen with access to the main application modules.

    Move creation with vehicle assignment.

    Vehicle registration for vehicles with and without driver.

    Vehicle rental module with filtering by capacity.

    Vehicle profile view with ratings and comments.

    Incident reporting linked to vehicles and services.

    Local offline persistence using Room Database.

Tech stack
Area	Technology
Language	Kotlin
UI	Jetpack Compose
Architecture	MVVM
Navigation	Navigation Compose
Local database	Room Database
State management	StateFlow and collectAsState
IDE	Android Studio
Version control	Git and GitHub
Minimum Android API	26
Target API	34
Java version	JDK 17+
Project status

The application reached a functional academic stage with implemented modules for authentication, move management, vehicle registration, rentals, and incident reporting. The documentation also indicates that the project required final testing, validation, and interface polishing before broader distribution.
Application structure

The codebase is organized around a typical Android separation of concerns, with UI, ViewModel, and data responsibilities clearly split as part of the MVVM design described in the project memory.

text
app/
├── src/main/java/com/[package]/appmudanza/
│   ├── data/
│   │   ├── local/
│   │   └── remote/
│   ├── domain/
│   ├── navigation/
│   ├── ui/
│   │   ├── auth/
│   │   ├── home/
│   │   ├── moves/
│   │   ├── rentals/
│   │   ├── vehicles/
│   │   └── incidents/
│   └── MainActivity.kt
├── res/
├── build.gradle.kts
└── proguard-rules.pro

Core modules
Authentication

The authentication module allows users to register with name, email, and password, and then log in with their credentials. After a successful login, the user is redirected to the Home screen.
Move management

The move management module supports creating a move with origin, destination, client, date, and an assigned vehicle with driver. It also includes a view for registered moves and filtering by vehicle capacity.
Vehicle registration

The app supports the registration of vehicles with driver and vehicles without driver through separate forms. Registered vehicles become available to the modules that depend on them, such as move management and rental screens.
Vehicle rental

The rental module displays available vehicles without driver, allows filtering by load capacity, and lets the user select a vehicle and consult its profile information.
Incident reporting

The incident module lets users select a related vehicle and submit a description of a problem encountered during a service. Incidents are stored in the local database and confirmed through the interface.
Requirements

    Android Studio Otter or newer.

    JDK 17 or newer.

    Android emulator with API 34 or a physical Android device.

    Gradle configured with Kotlin DSL.

Local setup

    Clone the repository.

    Open the project in Android Studio.

    Let Gradle sync all dependencies.

    Run the app on an emulator or physical device.

    Test the main flows: authentication, moves, rentals, vehicles, and incidents.

Architecture notes

The implementation is based on MVVM, with Jetpack Compose used for the interface layer, ViewModels for state and logic orchestration, and Room entities, DAOs, and repositories for persistence. The documentation also describes centralized navigation with Navigation Compose and reactive UI updates through StateFlow and collectAsState.
Portfolio note

This repository is presented as an academic and portfolio project. It showcases Android development skills in Kotlin, local data persistence, modular screen design, and mobile application architecture for a logistics-related use case.
# MudanzApp

Mobile Android application developed as a final academic project focused on the management of moving services, vehicle registration, rentals, and incident reporting. The project is built with Kotlin and Jetpack Compose, follows an MVVM architecture, and uses Room as its local persistence layer.[1]

## Overview

MudanzApp was created to simplify the organization of moving-related services through a single mobile application. The app centralizes user authentication, move creation, vehicle management, rentals, and incident reporting in a modular structure designed for Android devices.[1]

The technical implementation described in the project documentation uses Android Studio, Kotlin, Jetpack Compose, Navigation Compose, Room Database, and an MVVM-based architecture with reactive state management.[1]

## Main features

- User registration and login.[1]
- Home screen with access to the main application modules.[1]
- Move creation with vehicle assignment.[1]
- Vehicle registration for vehicles with and without driver.[1]
- Vehicle rental module with filtering by capacity.[1]
- Vehicle profile view with ratings and comments.[1]
- Incident reporting linked to vehicles and services.[1]
- Local offline persistence using Room Database.[1]

## Tech stack

| Area | Technology |
|---|---|
| Language | Kotlin [1] |
| UI | Jetpack Compose [1] |
| Architecture | MVVM [1] |
| Navigation | Navigation Compose [1] |
| Local database | Room Database [1] |
| State management | StateFlow and collectAsState [1] |
| IDE | Android Studio [1] |
| Version control | Git and GitHub [1] |
| Minimum Android API | 26 [1] |
| Target API | 34 [1] |
| Java version | JDK 17+ [1] |

## Project status

The application reached a functional academic stage with implemented modules for authentication, move management, vehicle registration, rentals, and incident reporting. The documentation also indicates that the project required final testing, validation, and interface polishing before broader distribution.[1]

## Application structure

The codebase is organized around a typical Android separation of concerns, with UI, ViewModel, and data responsibilities clearly split as part of the MVVM design described in the project memory.[1]

```text
app/
├── src/main/java/com/[package]/appmudanza/
│   ├── data/
│   │   ├── local/
│   │   └── remote/
│   ├── domain/
│   ├── navigation/
│   ├── ui/
│   │   ├── auth/
│   │   ├── home/
│   │   ├── moves/
│   │   ├── rentals/
│   │   ├── vehicles/
│   │   └── incidents/
│   └── MainActivity.kt
├── res/
├── build.gradle.kts
└── proguard-rules.pro
```

## Core modules

### Authentication

The authentication module allows users to register with name, email, and password, and then log in with their credentials. After a successful login, the user is redirected to the Home screen.[1]

### Move management

The move management module supports creating a move with origin, destination, client, date, and an assigned vehicle with driver. It also includes a view for registered moves and filtering by vehicle capacity.[1]

### Vehicle registration

The app supports the registration of vehicles with driver and vehicles without driver through separate forms. Registered vehicles become available to the modules that depend on them, such as move management and rental screens.[1]

### Vehicle rental

The rental module displays available vehicles without driver, allows filtering by load capacity, and lets the user select a vehicle and consult its profile information.[1]

### Incident reporting

The incident module lets users select a related vehicle and submit a description of a problem encountered during a service. Incidents are stored in the local database and confirmed through the interface.[1]

## Requirements

- Android Studio Otter or newer.[1]
- JDK 17 or newer.[1]
- Android emulator with API 34 or a physical Android device.[1]
- Gradle configured with Kotlin DSL.[1]

## Local setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle sync all dependencies.
4. Run the app on an emulator or physical device.
5. Test the main flows: authentication, moves, rentals, vehicles, and incidents.

## Architecture notes

The implementation is based on MVVM, with Jetpack Compose used for the interface layer, ViewModels for state and logic orchestration, and Room entities, DAOs, and repositories for persistence. The documentation also describes centralized navigation with Navigation Compose and reactive UI updates through StateFlow and collectAsState.[1]

## Portfolio note

This repository is presented as an academic and portfolio project. It showcases Android development skills in Kotlin, local data persistence, modular screen design, and mobile application architecture for a logistics-related use case.[1]

## License

This project is shared for personal, academic, and portfolio viewing purposes only. Commercial use, redistribution, publication, sublicensing, or derivative works are not allowed without the express written consent of the authors.

Copyright (c) 2026 Thamiris Bittencourt, Enrique Martínez, and Miguel Martínez.
