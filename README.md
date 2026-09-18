```markdown
# xCloudStream TR Repo 🇹🇷

Türkçe film ve dizi siteleri için [CloudStream](https://github.com/recloudstream/cloudstream) eklenti deposu.

Bu depo, en popüler Türkçe film/dizi sitelerinden içerik çekmek için geliştirilmiş CloudStream eklentilerini barındırır.

---

## 📦 Desteklenen Siteler

| # | Site Adı | URL | İçerik Türü |
|---|----------|-----|-------------|
| 1 | **HDFilmCehennemi** | [hdfilmcehennemi.nl](https://www.hdfilmcehennemi.nl) | Film, Dizi |
| 2 | **FullHDFilmizlesene** | [fullhdfilmizlesene.now](https://www.fullhdfilmizlesene.now/) | Film |
| 3 | **FilmMakinesi** | [filmmakinesi.to](https://filmmakinesi.to/) | Film, Dizi |
| 4 | **UltraFilmizle** | [ultrafilmizle.org](https://ultrafilmizle.org/) | Film |
| 5 | **720izle** | [720izle.com](https://720izle.com/) | Film |
| 6 | **HDFilmizle** | [hdfilmizle.vip](https://www.hdfilmizle.vip/) | Film, Dizi |
| 7 | **FilmKutusu** | [filmkutusu.com](https://filmkutusu.com/) | Film |
| 8 | **HDFilmizlesende** | [hdfilmizlesende.com](https://hdfilmizlesende.com/) | Film, Dizi |

---

## 🚀 Kurulum

### CloudStream'e Depo Ekleme

1. **CloudStream** uygulamasını açın.
2. **Ayarlar** → **Eklentiler** → **Depo Ekle** (veya **Extensions** → **Add Repository**) bölümüne gidin.
3. Aşağıdaki URL'yi girin:

```

https://raw.githubusercontent.com/tamersariii/xcloudstream_tr_repo/main/repo.json

```

4. **Ekle** butonuna tıklayın.
5. Eklentiler listesinden istediğiniz siteyi bulun ve **Yükle** butonuna tıklayın.
6. Eklenti yüklendikten sonra ana sayfada ilgili siteyi görebilirsiniz.

### Manuel Kurulum (İleri Düzey)

Eğer eklentileri manuel olarak yüklemek isterseniz:

1. [Releases](https://github.com/tamersariii/xcloudstream_tr_repo/releases) sayfasından istediğiniz `.cs` dosyasını indirin.
2. CloudStream'de **Ayarlar** → **Eklentiler** → **Yerel Eklenti Yükle** bölümüne gidin.
3. İndirdiğiniz dosyayı seçin.

---

## 🛠️ Geliştirme

### Gereksinimler

- **JDK 17** veya üzeri
- **Android SDK** (API 21+)
- **Gradle 8.2** (Wrapper ile birlikte gelir)
- **Git**

### Projeyi Klonlama

```bash
git clone https://github.com/tamersariii/xcloudstream_tr_repo.git
cd xcloudstream_tr_repo
```

Derleme

Tüm eklentileri derlemek için:

```bash
./gradlew makePluginsJson
```

Belirli bir eklentiyi derlemek için:

```bash
# Örnek: HDFilmCehennemi eklentisini derle
./gradlew :HDFilmCehennemi:make
```

Derlenen .cs dosyaları builds/ klasöründe oluşur.

Cihaza Yükleme (ADB ile)

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
├── .github/workflows/build.yml       # GitHub Actions CI
├── gradle/wrapper/                   # Gradle Wrapper dosyaları
├── HDFilmCehennemi/                  # Her site için bir klasör
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/com/xcloudstream/HDFilmCehennemi/
│       │   ├── HDFilmCehennemiPlugin.kt
│       │   └── HDFilmCehennemiProvider.kt
│       └── res/values/strings.xml
├── FullHDFilmizlesene/
├── FilmMakinesi/
├── UltraFilmizle/
├── 720izle/
├── HDFilmizle/
├── FilmKutusu/
├── HDFilmizlesende/
├── build.gradle.kts                  # Kök Gradle dosyası
├── settings.gradle.kts               # Modülleri içerir
├── gradle.properties
├── repo.json                         # CloudStream depo tanımı
├── .gitignore
└── README.md
```

---

🔄 GitHub Actions ile Otomatik Derleme

Bu depo, main branch'ine her push yapıldığında GitHub Actions aracılığıyla otomatik olarak derlenir.

Süreç:

1. Kod main branch'ine push edilir.
2. GitHub Actions tetiklenir.
3. Tüm eklentiler derlenir.
4. Derlenen .cs dosyaları ve plugins.json, builds branch'ine otomatik olarak deploy edilir.
5. CloudStream bu builds branch'inden güncellemeleri çeker.

Build Durumu: .github/workflows/build.yml dosyasını inceleyebilirsiniz.

---

🤝 Katkıda Bulunma

Katkılarınızı bekliyoruz! Yeni bir site eklemek veya mevcut eklentileri iyileştirmek için:

1. Bu repoyu fork edin.
2. Yeni bir branch oluşturun: git checkout -b yeni-site-eklentisi
3. Değişikliklerinizi yapın ve commit edin: git commit -m "Yeni site eklendi: XYZ"
4. Branch'inizi push edin: git push origin yeni-site-eklentisi
5. Bir Pull Request açın.

Yeni Site Eklerken Dikkat Edilmesi Gerekenler

· Site HTML yapısını Inspect Element ile inceleyin.
· CSS seçicilerini (div.film-box, article.film vb.) doğru belirleyin.
· Cloudflare koruması olan siteler için app.get() fonksiyonunun yeterli olup olmadığını test edin.
· Film ve dizi ayrımını URL'ye göre yapın (örn: /dizi/ içeriyorsa TvType.TvSeries).
· Eklentiyi hem search() hem de getMainPage() fonksiyonlarıyla test edin.

---

⚠️ Yasal Uyarı

Bu depo, yalnızca eğitim ve kişisel kullanım amaçlıdır. Eklentiler, herhangi bir içeriği barındırmaz veya dağıtmaz; sadece halka açık web sitelerinden veri çeker. Kullanıcılar, kendi ülkelerindeki telif hakkı yasalarına uymakla yükümlüdür.

Bu depoyu kullanan kişiler, içeriklerin yasallığından ve telif hakkı durumundan kendileri sorumludur. Geliştirici(ler), bu eklentilerin kullanımından doğabilecek herhangi bir yasal sorundan sorumlu tutulamaz.

Herhangi bir telif hakkı ihlali durumunda lütfen ilgili siteyi doğrudan bilgilendirin.

---

📄 Lisans

Bu proje MIT Lisansı altında lisanslanmıştır. Detaylar için LICENSE dosyasına bakın.

---

🙏 Teşekkürler

· CloudStream geliştiricilerine
· recloudstream/cloudstream-extensions reposuna katkıda bulunanlara
· Tüm katkıda bulunanlara ve kullanıcılara

---

📞 İletişim

· GitHub: @tamersariii
· Sorun Bildirimi: Issues

---

<p align="center">
  <b>⭐ Bu projeyi faydalı bulduysanız yıldız vermeyi unutmayın! ⭐</b>
</p>
```

---