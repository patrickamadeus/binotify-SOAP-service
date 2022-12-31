# Tubes 2 Epify 
> Halo! Selamat datang di repository SOAP Service!

Repository engine ini dibuat untuk memenuhi **Tugas Besar Mata Kuliah IF3110 Pengembangan Aplikasi Berbasis Web** yang ke-2 pada Semester II Tahun Akademik 2022/2023. 

## Table of Contents
- [Tubes 2 Epify](#tubes-2-epify)
  - [Table of Contents](#table-of-contents)
  - [Deskripsi](#deskripsi)
  - [Skema Basis Data](#skema-basis-data)
  - [Cara Instalasi](#cara-instalasi)
  - [Pembagian Tugas](#pembagian-tugas)

## Deskripsi
SOAP adalah sebuah protokol pengiriman pesan berbasis XML yang digunakan untuk mengakses layanan web melalui HTTP. Implementasinya pada tugas besar kali ini adalah untuk menangani pengajuan request subscription dan juga menerima approval/rejection dari admin Binotify Premium. 

## Skema Basis Data
![image](/readme_img/skema_basisdata.png)

## Cara Instalasi
1. Clone repository ini ke local
2. Buka terminal dari repository ini
3. Jalankan kode berikut 
``` 
    java -jar ./webservice/target/webservice-1.0-jar-with-dependencies.jar
```


## Pembagian Tugas
|             | <u>Server-side</u> 
| ----------- | ----------- |
| Logger | 13520109, 13520088 |
| Status | 13520109, 13520088 |
| Subscription | 13520109, 13520142   | 
| Validator | 13520088  | 
| Webservices | 13520109  | 

## Daftar Endpoint

Endpoint URL : `http://localhost:8080/webservice/services`

Endpoint | Payload | Response 
--- | --- | --- 
`createSubscriptionRequest` | creator_id, subscriber_id | status, message
`isSubscriptionValid` | creator_id, subscriber_id | status, message
`acceptSubscription` | creator_id, subscriber_id | status, message
`rejectSubscription` | creator_id, subscriber_id | status, message
`listSubscription` | {creator_id}, {subscriber_id} | status, message, value

> NB : {} adalah opsional
