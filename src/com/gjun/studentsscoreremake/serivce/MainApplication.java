package com.gjun.studentsscoreremake.serivce;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gjun.studentsscoreremake.model.StudentsReport;
import com.gjun.studentsscoreremake.model.StudentsScore;

public class MainApplication {

	public static void main(String[] args) {

		try {
			List<String> lines = readFile("D:/測試.csv"); // 讀取檔案
			StudentsReport report = generateReport(lines); // 處理資料並生成報告
			writeFile("D:/測試_new.csv", formatReport(report)); // 輸出報告為新檔案
			System.out.println(report); // 顯示報告內容
		} catch (IOException | IllegalAccessException e) {
			System.err.println("檔案處理失敗：" + e.getMessage());
			e.printStackTrace();
		}
	}

	// 讀取檔案並檢查是否為空
	private static List<String> readFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		if (!new File(filePath).exists()) {
			throw new IOException("檔案不存在");
		}
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		if (lines.isEmpty()) {
			throw new IOException("檔案為空");
		}
		return lines;
	}

	// 寫入檔案
	private static void writeFile(String filePath, List<String> content) throws IOException {
		Files.write(Paths.get(filePath), content, StandardCharsets.UTF_8);
	}

	// 生成報告資料
	private static StudentsReport generateReport(List<String> lines) throws IllegalAccessException {
		StudentsReport report = new StudentsReport();
		List<StudentsScore> studentsScores = new ArrayList<>();

		report.setReportTitle(lines.get(0)); // 第一行為標題

		// 從第二行開始處理學生資料
		for (int i = 1; i < lines.size(); i++) {
			studentsScores.add(createStudent(lines.get(i)));
		}

		// 排序學生資料
		Comparator<StudentsScore> comparator = new Comparator<StudentsScore>() {
			@Override
			public int compare(StudentsScore o1, StudentsScore o2) {
				int o1Sum = Integer.parseInt(o1.getSumScore());
				int o2Sum = Integer.parseInt(o2.getSumScore());

				int o1Index0 = Integer.parseInt(o1.getScore().get(0));
				int o2Index0 = Integer.parseInt(o2.getScore().get(0));

				int o1Index2 = Integer.parseInt(o1.getScore().get(2));
				int o2Index2 = Integer.parseInt(o2.getScore().get(2));

				if (o2Sum != o1Sum) {
					return o2Sum - o1Sum;
				} else if (o2Index0 != o1Index0) {
					return o2Index0 - o1Index0;
				} else {
					return o2Index2 - o1Index2;
				}
			}
		};

		Collections.sort(studentsScores, comparator);

		// 設置排名
		int rank = 1;
		for (StudentsScore student : studentsScores) {
			student.setRank(String.valueOf(rank++));
		}

		report.setStudentsScoreDates(studentsScores);
		return report;
	}

	// 解析單筆學生資料
	private static StudentsScore createStudent(String line) {

		List<String> scores = new ArrayList<>(Arrays.asList(line.split(",")));

		StudentsScore student = new StudentsScore();
		student.setStudentNo(scores.remove(0)); // 移除座號
		student.setName(scores.remove(0)); // 移除姓名
		BigDecimal sum = calculateSum(scores);
		student.setScore(scores);
		student.setSumScore(sum.toString());
		student.setAverage(calculateAverage(sum, scores.size()));

		return student;
	}

	// 計算總成績
	private static BigDecimal calculateSum(List<String> scores) {
		BigDecimal sum = BigDecimal.ZERO;
		for (String score : scores) {
			if (isNumeric(score)) {
				sum = sum.add(new BigDecimal(score));
			}
		}
		return sum;
	}

	// 計算平均分數
	private static String calculateAverage(BigDecimal sum, int count) {
		return sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP).toString();
	}

	// 檢查是否為數字
	private static boolean isNumeric(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 格式化報告為文字
	private static List<String> formatReport(StudentsReport report) throws IllegalAccessException {
		List<String> newFile = new ArrayList<>();
		newFile.add(report.getReportTitle());

		Field[] fields = StudentsScore.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}

		for (StudentsScore student : report.getStudentsScoreDates()) {
			StringBuilder newLine = new StringBuilder();
			for (Field field : fields) {
				Object value = field.get(student);
				if (value != null) {
					if (value instanceof Collection) {
						List<String> subjects = (List<String>) value;
						for (String subject : subjects) {
							newLine.append(subject).append(",");
						}
					} else {
						newLine.append(value).append(",");
					}
				}
			}
			// 去掉最後多餘的逗號
			newFile.add(newLine.substring(0, newLine.length() - 1));
		}
		return newFile;
	}
}
