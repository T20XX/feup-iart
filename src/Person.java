
public class Person {
	
	private int age;
	private Profession profession;
	private Hobby[] hobbies;
	private Group group;
	
	public Person(int age, Profession profession, Hobby[] hobbies){
		this.age = age;
		this.profession = profession;
		this.hobbies = hobbies;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Profession getProfession() {
		return profession;
	}
	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	public Hobby[] getHobbies() {
		return hobbies;
	}
	public void setHobbies(Hobby[] hobbies) {
		this.hobbies = hobbies;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}

}
