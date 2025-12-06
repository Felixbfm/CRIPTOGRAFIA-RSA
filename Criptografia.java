import java.util.Scanner;
import java.util.ArrayList;

//Oi professor!
public class Criptografia {
    public static void main(String Args[]) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("p: ");
        int p = sc.nextInt();
        
        System.out.print("q: ");
        int q = sc.nextInt();
        
        long n = p * q;
        long phi = (p - 1) * (q - 1);
        
        long e = 3;
        while (e < phi && mdc(e, phi) != 1) {
            e += 2;
        }
        
        long d = inverso(e, phi);
        
        System.out.println("\nn: " + n);
        System.out.println("φ(n): " + phi);
        System.out.println("e: " + e);
        System.out.println("d: " + d);
        
        sc.nextLine();
        System.out.print("\nTexto (A-Z e espaço apenas): ");
        String texto = sc.nextLine().toUpperCase();
        
        ArrayList<Integer> valores = new ArrayList<>();
        for (char c : texto.toCharArray()) {
            if (c == ' ') {
                valores.add(99);
            } else if (c >= 'A' && c <= 'Z') {
                valores.add(10 + (c - 'A'));
            }
        }
        
        System.out.print("\nValores (A=10,...,Z=35, espaço=99): ");
        for (int v : valores) System.out.print(v + " ");
        
        ArrayList<Long> cripto = new ArrayList<>();
        for (int v : valores) {
            long m = v;
            if (m >= n) m = m % n;
            long c = modPow(m, e, n);
            cripto.add(c);
        }
        
        System.out.print("\nCriptografado: ");
        for (long c : cripto) System.out.print(c + " ");
        
        StringBuilder original = new StringBuilder();
        for (int i = 0; i < cripto.size(); i++) {
            long c = cripto.get(i);
            long m = modPow(c, d, n);
            
            boolean encontrou = false;
            for (int testVal = 10; testVal <= 35; testVal++) {
                if (testVal % n == m % n) {
                    original.append((char) ('A' + (testVal - 10)));
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou && m % n == 99 % n) {
                original.append(' ');
            } else if (!encontrou) {
                original.append('?');
            }
        }
        
        System.out.println("\nDescriptografado: " + original.toString());
        
        sc.close();
    }
    
    public static long mdc(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static long inverso(long a, long m) {
        long m0 = m;
        long y = 0, x = 1;
        while (a > 1) {
            long q = a / m;
            long t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if (x < 0) x += m0;
        return x;
    }
    
    public static long modPow(long base, long exp, long mod) {
        long resultado = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1) resultado = (resultado * base) % mod;
            exp >>= 1;
            base = (base * base) % mod;
        }
        return resultado;
    }
}
