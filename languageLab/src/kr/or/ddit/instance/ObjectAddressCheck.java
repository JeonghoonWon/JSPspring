package kr.or.ddit.instance;

import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

public class ObjectAddressCheck {

	public static void main(String[] args) {
		String s1 = "test";
		String s2 = "test";
		
		VirtualMachine vm = VM.current();
		System.out.printf("s1's address : %s\n", vm.addressOf(s1));
		System.out.printf("s2's address : %s\n", vm.addressOf(s1));
		System.out.println(s1==s2);
		
		String s3 = new String("test");
		String s4 = new String("test");
		
		System.out.printf("s3's address : %s\n",vm.addressOf(s3));
		System.out.printf("s4's address : %s\n",vm.addressOf(s4));
		System.out.println(s3==s4);
		
		String str = "constant";
		System.out.printf("str's address : %s\n",vm.addressOf(str));
		str += "modify";
		System.out.printf("str's address : %s\n",vm.addressOf(str));
		
		String str2 = "constant";
		System.out.printf("str2's address : %s\n",vm.addressOf(str2));
		
		StringBuffer sb = new StringBuffer("buffer");
		System.out.printf("sb's address : %s\n", vm.addressOf(sb));
		sb.append("modify");
		System.out.printf("sb's address : %s\n", vm.addressOf(sb));
	}

}
