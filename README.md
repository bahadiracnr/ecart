# 🚀 eCart

**eCart**, mikroservis mimarisiyle geliştirilen, modern, esnek ve ölçeklenebilir bir **e-ticaret altyapısıdır**.  
Yapı: **Monorepo + Multi Module** 🔥  
Her servis **bağımsız** çalışır; ortak yapılar **parent projesi** altında toplanır.

---

## 📦 Proje Yapısı

```
ecart/
├── auth-service/           # Kimlik doğrulama ve kullanıcı yönetimi
├── store-service/          # Ürün ve stok yönetimi
├── notification-service/   # E-posta bildirim servisi (gRPC + SMTP)
├── parent/                 # Ortak bağımlılıklar ve yapılandırmalar
├── compose/                # Docker Compose dosyaları
└── .idea/                  # IDE konfigürasyon dosyaları
```

---

## ⚙️ Kullanılan Teknolojiler

- Java 21
- Spring Boot 3
- PostgreSQL
- Redis
- Kafka (opsiyonel)
- gRPC
- Docker Compose
- Flyway
- JWT Authentication

---

## 🏗️ Mikroservisler

| Servis | Açıklama | Teknolojiler |
| :--- | :--- | :--- |
| **auth-service** | Kullanıcı kayıt, giriş, JWT doğrulama | Spring Security, JWT |
| **store-service** | Ürün oluşturma, listeleme, yönetim | PostgreSQL, Redis, gRPC Client |
| **notification-service** | E-posta gönderimi (sipariş bildirimi vb.) | gRPC Server, Spring Mail (SMTP) |

---

## 🛠️ Çalıştırmak İçin

### Gereksinimler

- Java 21+
- Maven 3.8+
- Docker & Docker Compose

### Başlatmak

```bash
docker-compose up --build
```

Alternatif olarak:

```bash
cd auth-service
./mvnw spring-boot:run
```

---

## 📢 Özellikler

- Mikroservis tabanlı mimari
- Her servis bağımsız ölçeklenebilir
- gRPC ile yüksek performanslı iletişim
- Redis ile hızlı veri erişimi
- JWT ile güvenli authentication
- Flyway ile otomatik veritabanı migration
- Docker Compose ile kolay orkestrasyon

---

## 🧩 İlerleyen Planlar

- Ürün yorum ve puanlama servisi (review-service)
- Sepet ve sipariş yönetimi (cart-service & order-service)
- Admin panel ve istatistikler
- OAuth2 entegrasyonu

---

## 🤝 Katkı Sağlamak İster misin?

Pull request'ler ve issue'lar her zaman hoş karşılanır!  
Bir fikrin mi var? ➡️ [Issue aç](https://github.com/bahadiracnr/ecart/issues)

---

## 🧙‍♂️ Yazar

**Bahadır Acuner**  
🔗 [GitHub Profilim](https://github.com/bahadiracnr)

---

## ⭐ Desteklemek için

Bu projeye yıldız vermeyi unutma! ⭐  

> "eCart, modern e-ticaret projeleri için hızlı ve güçlü bir temel oluşturur."
