# xCloudStream TR Repo 🇹🇷

Türkçe film ve dizi siteleri için [CloudStream](https://github.com/recloudstream/cloudstream) eklenti deposu.

Bu depo, CloudStream **4.8.0 PRE** ve üzeri sürümlerle uyumludur.

---

## 📦 Desteklenen Siteler

| # | Eklenti Adı | Kaynak Site | İçerik Türü |
|---|-------------|-------------|-------------|
| 1 | **HDFilmCehennemi** | [hdfilmcehennemi.nl](https://www.hdfilmcehennemi.nl) | Film, Dizi |
| 2 | **FullHDFilmizlesene** | [fullhdfilmizlesene.now](https://www.fullhdfilmizlesene.now/) | Film |
| 3 | **FilmMakinesi** | [filmmakinesi.to](https://filmmakinesi.to/) | Film, Dizi |
| 4 | **UltraFilmizle** | [ultrafilmizle.org](https://ultrafilmizle.org/) | Film |
| 5 | **HDFilmizle** | [hdfilmizle.vip](https://www.hdfilmizle.vip/) | Film, Dizi |
| 6 | **FilmKutusu** | [filmkutusu.com](https://filmkutusu.com/) | Film |
| 7 | **HDFilmizlesende** | [hdfilmizlesende.com](https://hdfilmizlesende.com/) | Film, Dizi |

Toplam **7 eklenti** — Türkçe içerik, Türkçe arayüz.

---

## 🚀 Kurulum

### CloudStream'e Depo Ekleme

1. **CloudStream** uygulamasını açın.
2. **Ayarlar** → **Eklentiler** → **Depo Ekle** bölümüne gidin.
3. Aşağıdaki URL'yi yapıştırın:

```

https://raw.githubusercontent.com/tamersariii/xcloudstream_tr_repo/main/repo.json

```

4. **Ekle** butonuna tıklayın.
5. Eklenti listesinde 7 eklenti görünecektir. İstediğinizi seçip **Yükle** butonuna basın.
6. Yükledikten sonra ana sayfada ilgili siteyi görebilirsiniz.

### Kısa Kod ile Ekleme (Opsiyonel)

CloudStream'de depoyu ekledikten sonra, depo menüsünden **"Kısa Kodu Göster"** seçeneğiyle otomatik olarak oluşturulan kısa kodu arkadaşlarınızla paylaşabilirsiniz.

---

## 🛠️ Geliştirme

### Gereksinimler

| Araç | Minimum Sürüm |
|------|---------------|
| JDK | 17 |
| Android SDK | API 21+ |
| Gradle | 8.14.4 (Wrapper ile gelir) |
| Git | 2.30+ |

### Projeyi Klonlama

```bash
git clone https://github.com/tamersariii/xcloudstream_tr_repo.git
cd xcloudstream_tr_repo
```

Derleme

Tüm eklentileri .aar olarak derlemek için:

```bash
./gradlew assembleDebug
```

Belirli bir eklentiyi derlemek için:

```bash
./gradlew :HDFilmCehennemi:assembleDebug
```

Derlenen .aar dosyaları HDFilmCehennemi/build/outputs/aar/ altında oluşur.

.aar → .cs Dönüşümü

CloudStream eklentileri aslında .aar (Android Archive) dosyalarıdır. CloudStream, .cs uzantılı dosyaları kabul eder. Bu dönüşüm GitHub Actions tarafından otomatik olarak yapılır:

```bash
mkdir -p builds
find . -path "*/build/outputs/aar/*-debug.aar" -exec cp {} builds/ \;
```

.cs Dosyasını Cihaza Yükleme (ADB ile)

```bash
./gradlew :HDFilmCehennemi:deployWithAdb
```

Hata Ayıklama

```bash
adb logcat -v brief | grep -i "cloudstream\|HDFilmCehennemi"
```

---

📁 Proje Yapısı

```
xcloudstream_tr_repo/
├── .github/
│   └── workflows/
│       └── build.yml                       # GitHub Actions CI/CD
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties       # Gradle 8.14.4
├── gradlew
├── gradlew.bat
├── build.gradle.kts                        # Kök Gradle yapılandırması
├── settings.gradle.kts                     # Modül listesi
├── gradle.properties
├── repo.json                               # CloudStream depo tanımı
├── .gitignore
├── LICENSE
├── README.md
│
├── HDFilmCehennemi/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/com/xcloudstream/HDFilmCehennemi/
│       │   ├── HDFilmCehennemiPlugin.kt
│       │   └── HDFilmCehennemiProvider.kt
│       └── res/values/strings.xml
│
├── FullHDFilmizlesene/                     # (aynı yapı)
├── FilmMakinesi/                           # (aynı yapı)
├── UltraFilmizle/                          # (aynı yapı)
├── HDFilmizle/                             # (aynı yapı)
├── FilmKutusu/                             # (aynı yapı)
└── HDFilmizlesende/                        # (aynı yapı)
```

---

🔄 GitHub Actions ile Otomatik Derleme

Bu depo, main branch'ine her push yapıldığında GitHub Actions aracılığıyla otomatik olarak derlenir.

Süreç

1. Kod main branch'ine push edilir.
2. GitHub Actions tetiklenir.
3. ./gradlew assembleDebug komutu ile tüm modüller derlenir.
4. .aar dosyaları .cs uzantısıyla builds/ klasörüne kopyalanır.
5. plugins.json dosyası otomatik olarak oluşturulur.
6. Derlenen tüm dosyalar builds branch'ine otomatik olarak deploy edilir.
7. CloudStream bu builds branch'inden güncellemeleri çeker.

Build Durumu

.github/workflows/build.yml dosyasını inceleyebilirsiniz.

builds branch'indeki dosyalar:

· plugins.json — Eklenti listesi
· HDFilmCehennemi.cs
· FullHDFilmizlesene.cs
· FilmMakinesi.cs
· UltraFilmizle.cs
· HDFilmizle.cs
· FilmKutusu.cs
· HDFilmizlesende.cs

---

🤝 Katkıda Bulunma

Katkılarınızı bekliyoruz! Yeni bir site eklemek veya mevcut eklentileri iyileştirmek için:

1. Bu repoyu fork edin.
2. Yeni bir branch oluşturun:
   ```bash
   git checkout -b yeni-site-eklentisi
   ```
3. Değişikliklerinizi yapın ve commit edin:
   ```bash
   git commit -m "Yeni site eklendi: XYZ"
   ```
4. Branch'inizi push edin:
   ```bash
   git push origin yeni-site-eklentisi
   ```
5. Bir Pull Request açın.

Yeni Site Eklerken Dikkat Edilmesi Gerekenler

· Site HTML yapısını Inspect Element ile inceleyin.
· CSS seçicilerini (div.film-box, article.film vb.) doğru belirleyin.
· Cloudflare koruması olan siteler için app.get() yeterli olmayabilir; ek header'lar gerekebilir.
· Film ve dizi ayrımını URL'ye göre yapın (örn: /dizi/ içeriyorsa TvType.TvSeries).
· Eklentiyi hem search() hem de getMainPage() fonksiyonlarıyla test edin.
· Yeni modülü settings.gradle.kts'ye ekleyin.

---

⚠️ Sık Karşılaşılan Sorunlar

Sorun: "Eklenti bulunamadı" hatası

Çözüm: CloudStream sürümünüzün 4.8.0 PRE veya üzeri olduğundan emin olun. Daha eski sürümler apiVersion: 1'i desteklemeyebilir.

Sorun: Depo eklendi ama eklentiler görünmüyor

Çözüm:

1. CloudStream önbelleğini temizleyin: Ayarlar → Genel → Önbelleği Temizle
2. Uygulamayı tamamen kapatıp yeniden açın
3. Depoyu silip yeniden ekleyin

Sorun: Eklenti yüklendi ama kaynaklar gelmiyor

Çözüm: İlgili site HTML yapısını değiştirmiş olabilir. Eklentiyi güncellemek için bir Issue açın veya Pull Request gönderin.

Sorun: Build başarısız oluyor

Çözüm: GitHub Actions sekmesinden hata loglarını inceleyin. Genellikle Kotlin sürümü veya AGP uyumsuzluğundan kaynaklanır.

---

📄 Lisans

Bu proje MIT Lisansı altında lisanslanmıştır. Detaylar için LICENSE dosyasına bakın.

---

🙏 Teşekkürler

· CloudStream geliştiricilerine
· recloudstream/extensions reposuna katkıda bulunanlara
· Tüm katkıda bulunanlara ve kullanıcılara

---

📞 İletişim

· GitHub: @tamersariii
· Sorun Bildirimi: Issues
· Pull Request: Pull Requests

---

<p align="center">
  <b>⭐ Bu projeyi faydalı bulduysanız yıldız vermeyi unutmayın! ⭐</b>
</p>
```

---
