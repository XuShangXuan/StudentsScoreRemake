package com.gjun.studentsscoreremake.model;

import java.util.List;

public class StudentsReport {

	private String reportTitle;

	private List<StudentsScore> studentsScoreDates;

	public String getReportTitle() {
		return reportTitle + ",總分,平均,排名";
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public List<StudentsScore> getStudentsScoreDates() {
		return studentsScoreDates;
	}

	public void setStudentsScoreDates(List<StudentsScore> studentsScoreDates) {
		this.studentsScoreDates = studentsScoreDates;
	}

	@Override
	public String toString() {
		return "StudentsReport [reportTitle=" + reportTitle + ", studentsScoreDates=" + studentsScoreDates + "]";
	}

}
