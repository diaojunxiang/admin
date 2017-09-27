package entity;

import java.util.List;

public class BanJi {
	private int id;
	private String name;
	private int stuNums;
	private List<Subject> subs;

	public List<Subject> getSubs() {
		return subs;
	}

	public void setSubs(List<Subject> subs) {
		this.subs = subs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStuNums() {
		return stuNums;
	}

	public void setStuNums(int stuNums) {
		this.stuNums = stuNums;
	}
}
