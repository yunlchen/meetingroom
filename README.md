# meetingroom

---

```
A simple service for meeting room reservation design
```

## System design in object oriented method

### Fundamental

 * Accept meeting room reservation
   * Return `true` or `false` to indicate whether your reservation is accepted or not
 * Show meeting room's existing reservation schedule by a given time range or date

### Nice to have

 * Expose your service on the internet via restful API by leveraging on `Spring Boot`
 * Persist your data under MySQL (why you need this?) - `JPA` under `Spring Boot`
 * [*] Is your system able to accept the request under an multi-thread environment?

## Checkpoints
 * Check local environment
   * `java -version`
   * `gradle -version`
   * `git config --list`
* Git clone from the remote repository
  * `git clone https://github.com/yunlchen/meetingroom.git`
* Run project
  * Run 'Hello world!' in your local IJ
  * Run gradle command `gradle clean build` in your terminal
