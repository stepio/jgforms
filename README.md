# jgforms

[![Build Status](https://travis-ci.com/stepio/jgforms.svg?branch=master)](https://travis-ci.com/stepio/jgforms)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=stepio_jgforms&metric=alert_status)](https://sonarcloud.io/dashboard?id=stepio_jgforms)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6886f76bbc2347a19fe5dda0978038a9)](https://app.codacy.com/app/stepio/jgforms?utm_source=github.com&utm_medium=referral&utm_content=stepio/jgforms&utm_campaign=Badge_Grade_Dashboard)
[![DepShield Badge](https://depshield.sonatype.org/badges/stepio/jgforms/depshield.svg)](https://depshield.github.io)
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

Google's form submission using Java API. No third-party dependencies (except of test-scoped), compatible with Java 7 and Android.

Suppose you have a Google Form with next URL:
```properties
https://docs.google.com/forms/d/1Gn8J8I-5k-0qETKchs-P2UOs3kCU17uHs0GtqCklgLk/edit
```
This part is "form's key": `1Gn8J8I-5k-0qETKchs-P2UOs3kCU17uHs0GtqCklgLk`.

When you submit this form and check the resulting request through "DevTools", you can find next form data submitted:
```properties
entry.1464627081: 42
entry.786688631: This text is just dummy
entry.1117050788_year: 2019
entry.1117050788_month: 1
entry.1117050788_day: 10
entry.1117050788_hour: 11
entry.1117050788_minute: 46
entry.1536399354_hour: 09
entry.1536399354_minute: 23
entry.1536399354_second: 14
```
The above given numbers in each of the `entry.<number>: value` lines identify the appropriate questions in your Google Form.
So while using the project you will need them along with the above mentioned form key.

To submit data to your form using this project, just follow next steps:
1.  Prepare metadata to match your Google Form:
```java
public enum MyForm implements MetaData {

    SOME_SHORT_TEXT(1464627081L),
    MUCH_LONGER_TEXT(786688631L),
    EXTRA_DATE(1117050788L),
    SAMPLE_DURATION(1536399354L);

    private long id;

    JGForm(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }
}
```
2.  Use metadata to prepare form data and construct `URL` object with it:
```java
Calendar date = Calendar.getInstance();
Calendar from = Calendar.getInstance();
from.set(date.get(Calendar.YEAR) - 1, date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 1, 2, 3);
URL url = Builder.formKey("1FAIpQLScahJirT2sVrm0qDveeuiO1oZBJ5B7J0gdeI7UAZGohKEmi9g")
        .put(MyForm.SOME_SHORT_TEXT, 42)
        .put(MyForm.MUCH_LONGER_TEXT, "Any long text could be here")
        .putDateTime(MyForm.EXTRA_DATE, date)
        .putDuration(MyForm.SAMPLE_DURATION, from, date)
        .toUrl();
```
The above given code generates next URL:
```properties
https://docs.google.com/forms/d/e/1FAIpQLScahJirT2sVrm0qDveeuiO1oZBJ5B7J0gdeI7UAZGohKEmi9g/formResponse?entry.1117050788_month=1&entry.1117050788_day=13&entry.1117050788_year=2019&entry.1536399354_minute=16&entry.1536399354_second=43&entry.1117050788_hour=14&entry.1117050788_minute=18&entry.786688631=Any+long+text+could+be+here&entry.1536399354_hour=13&entry.1464627081=42
```
3.  Submit the form data:
```java
Submitter submitter = new Submitter(
    new Configuration()
);
try {
   submitter.submitForm(url);
} catch (NotSubmittedException ex) {
    // TODO: log & handle the exception properly
}
```
