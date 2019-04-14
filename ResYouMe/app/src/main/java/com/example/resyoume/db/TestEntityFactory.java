package com.example.resyoume.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEntityFactory {

    public static Resume createTestResume() {
        Contact contact =
                new Contact(
                        0,
                        new Date(),
                        "John", "Doe",
                        "Mr.",
                        "1600 Amphitheatre Parkway", "94043", "Mountain View", "CA", "United States",
                        "john.doe@example.com", "987 555 1234", "linkedin.com/john-example-doe",
                        "Being average, watching grass grow, watching paint dry",
                        "101 Ways to Blend In",
                        "This user was manually input and thus has no accompanying plaintext.",
                        null,
                        null
                );

        List<WorkPhase> workPhases = new ArrayList<>();
        workPhases.add(
                new WorkPhase(
                        0,
                        "2018-01", "2018-07",
                        "Groundskeeper",
                        "Google",
                        "United States",
                        "This phase was manually input and thus has no accompanying plaintext."
                )
        );

        workPhases.add(
                new WorkPhase(
                        0,
                        "2017-10", "2017-12",
                        "Painter",
                        "Microsoft",
                        "United States",
                        "This phase (2) was manually input and thus has no accompanying plaintext."
                )
        );

        List<EducationPhase> educationPhases = new ArrayList<>();
        educationPhases.add(
                new EducationPhase(
                        0,
                        "2013-08", "2017-06",
                        "University of South Dakota",
                        "United States",
                        "This phase was manually input and thus has no accompanying plaintext."
                )
        );

        return new Resume(contact, educationPhases, workPhases);
    }

    public static Resume createTestResumeLongName() {
        Contact contact =
                new Contact(
                        0,
                        new Date(),
                        "Johnathaniel", "Doemoronicostis",
                        "Mr.",
                        "1600 Amphitheatre Parkway", "94043", "Mountain View", "CA", "United States",
                        "john.doe@example.com", "987 555 1234", "linkedin.com/john-example-doe",
                        "Being average, watching grass grow, watching paint dry",
                        "101 Ways to Blend In",
                        "This user was manually input and thus has no accompanying plaintext.",
                        null,
                        null
                );

        List<WorkPhase> workPhases = new ArrayList<>();
        workPhases.add(
                new WorkPhase(
                        0,
                        "2018-01", "2018-07",
                        "Groundskeeper",
                        "Google",
                        "United States",
                        "This phase was manually input and thus has no accompanying plaintext."
                )
        );

        workPhases.add(
                new WorkPhase(
                        0,
                        "2017-10", "2017-12",
                        "Painter",
                        "Microsoft",
                        "United States",
                        "This phase (2) was manually input and thus has no accompanying plaintext."
                )
        );

        List<EducationPhase> educationPhases = new ArrayList<>();
        educationPhases.add(
                new EducationPhase(
                        0,
                        "2013-08", "2017-06",
                        "University of South Dakota",
                        "United States",
                        "This phase was manually input and thus has no accompanying plaintext."
                )
        );

        return new Resume(contact, educationPhases, workPhases);
    }

    public static CompanyInfo createTestCompanyInfo() {
        return new CompanyInfo(
                0,
                new Date(),
                "Eustace's Ranch",
                "Nowhere, KS",
                "@BaggeRancher",
                "https://www.eustace.ranch/index.html",
                "Eustace built up his ranch from the ground up, driven by a passion for farming and his wife Muriel. He hates stupid dogs.",
                "https://www.linkedin.com/company/bagge-ranch",
                "This is an example set of company info for testing the database.",
                null,
                null);
    }
}
