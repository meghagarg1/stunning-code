package com.example.stunningcode;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVImporter {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public ImportResult importStream(InputStream csvStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
            // Skip header
            List<String> lines = br.lines().skip(1).toList();

            Set<String> seenIds = new HashSet<>();

            List<UserRecord> records = new ArrayList<>();
            List<ImportError> errors = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                int row = i + 2; // header counted as row 1
                String[] cols = line.split(",", -1);

                if (cols.length < 4) {
                    errors.add(new ImportError(row, "Not enough columns"));
                    continue;
                }

                String id = cols[0].trim();
                String name = cols[1].trim();
                String email = cols[2].trim();
                String ageStr = cols[3].trim();

                // Functional validation
                Optional<String> error = validateRow(id, email, ageStr, seenIds);
                if (error.isPresent()) {
                    errors.add(new ImportError(row, error.get()));
                    continue;
                }

                int age = Integer.parseInt(ageStr);
                seenIds.add(id);
                records.add(new UserRecord(id, name, email, age));
            }

            return new ImportResult(records, errors);
        }
    }

    private Optional<String> validateRow(String id, String email, String ageStr, Set<String> seenIds) {
        if (id.isEmpty()) return Optional.of("Missing id");
        if (seenIds.contains(id)) return Optional.of("Duplicate id: " + id);
        if (!EMAIL.matcher(email).matches()) return Optional.of("Invalid email: " + email);
        try {
            int age = Integer.parseInt(ageStr);
            if (age < 0) return Optional.of("Invalid age: " + ageStr);
        } catch (NumberFormatException e) {
            return Optional.of("Invalid age: " + ageStr);
        }
        return Optional.empty();
    }


    public static void main(String[] args) throws Exception {
        String csv = "id,name,email,age\n" +
                "u1,Megha,megha@example.com,30\n" +
                "u2,Alpha,bad-email,25\n" +
                "u1,Duplicate,dup@example.com,22\n" +
                "u3,Young,user3@example.com,-1\n";

        CSVImporter importer = new CSVImporter();
        ImportResult res = importer.importStream(new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8)));

        System.out.println("Records:");
        res.userRecordList.forEach(System.out::println);
        System.out.println("Errors:");
        res.importErrorList.forEach(System.out::println);
    }
//    public static void main(String[] args){
//        ImportResult imp = new ImportResult();
//        imp.importErrorList.add(new ImportError(1, "error"));
//        imp.userRecordList.add(new UserRecord("123", "MEGS", "megs@gmail.com", 12));
//        System.out.println(imp.toString());
//        System.out.println("Blah Blah");
//    }
}


class UserRecord{
    final String id;
    final String name;
    final String email;
    final int age;

    UserRecord(String id, String name, String email, int age){
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}

class ImportError{
    final int row;
    final String error;

    ImportError(int row, String error) {
        this.row = row;
        this.error = error;
    }

    @Override
    public String toString() {
        return "ImportError{" +
                "row=" + row +
                ", error='" + error + '\'' +
                '}';
    }
}

class ImportResult{
    final List<UserRecord> userRecordList;
    final List<ImportError> importErrorList;

    public ImportResult(List<UserRecord> userRecordList, List<ImportError> importErrorList) {
        this.userRecordList = userRecordList;
        this.importErrorList = importErrorList;
    }

    @Override
    public String toString() {
        return "ImportResult{" +
                "userRecordList=" + userRecordList +
                ", importErrorList=" + importErrorList +
                '}';
    }
}
