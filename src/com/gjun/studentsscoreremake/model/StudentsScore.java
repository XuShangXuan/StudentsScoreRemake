package com.gjun.studentsscoreremake.model;

import java.util.List;

public class StudentsScore {

	private String studentNo;

	private String name;

	private List<String> score;

	private String sumScore;

	private String average;

	private String rank;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getScore() {
		return score;
	}

	public void setScore(List<String> score) {
		this.score = score;
	}

	public String getSumScore() {
		return sumScore;
	}

	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "StudentsScore [studentNo=" + studentNo + ", name=" + name + ", score=" + score + ", sumScore="
				+ sumScore + ", average=" + average + ", rank=" + rank + "]";
	}

}
