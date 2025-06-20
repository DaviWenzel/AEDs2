import java.util.Random;

public class SortingPerformanceAnalysis {

    // Variáveis globais para contar comparações e movimentações
    private static long comparisons = 0;
    private static long movements = 0; // Alterado para long

    // Método para gerar um vetor de inteiros aleatórios de um determinado tamanho
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(100000); // Números aleatórios até 100000
        }
        return array;
    }

    // Algoritmo Selection Sort
    public static void selectionSort(int[] arr) {
        comparisons = 0;
        movements = 0;
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                movements += 3; // Troca 3 elementos
            }
        }
        System.out.println("selectionsort " + arr.length + " = " + comparisons + " comparações, " + movements + " movimentações");
    }

    // Algoritmo Insertion Sort
    public static void insertionSort(int[] arr) {
        comparisons = 0;
        movements = 0;
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            comparisons++;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                movements++;
            }
            arr[j + 1] = key;
            movements++;
        }
        System.out.println("insertionsort " + arr.length + " = " + comparisons + " comparações, " + movements + " movimentações");
    }

    // Algoritmo Bubble Sort
    public static void bubbleSort(int[] arr) {
        comparisons = 0;
        movements = 0;
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    movements += 3; // Troca 3 elementos
                }
            }
        }
        System.out.println("bubblesort " + arr.length + " = " + comparisons + " comparações, " + movements + " movimentações");
    }

    // Algoritmo Quicksort
    public static void quicksort(int[] arr) {
        comparisons = 0;
        movements = 0;
        long startTime = System.nanoTime();
        quicksortHelper(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        System.out.println("quicksort " + arr.length + " = " + comparisons + " comparações, " + movements + " movimentações");
        System.out.println("Time: " + (endTime - startTime) / 1000 + " µs"); // Agora está em microsegundos
    }

    private static void quicksortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quicksortHelper(arr, low, pi - 1);
            quicksortHelper(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                movements += 3; // Troca 3 elementos
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        movements += 3; // Troca 3 elementos
        return i + 1;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};

        for (int size : sizes) {
            System.out.println("Tamanho do vetor: " + size);

            int[] array;

            array = generateRandomArray(size);
            long startTime = System.nanoTime();
            selectionSort(array.clone());
            long endTime = System.nanoTime();
            System.out.println("Time: " + (endTime - startTime) / 1000 + " µs"); // Em microsegundos

            array = generateRandomArray(size);
            startTime = System.nanoTime();
            insertionSort(array.clone());
            endTime = System.nanoTime();
            System.out.println("Time: " + (endTime - startTime) / 1000 + " µs"); // Em microsegundos

            array = generateRandomArray(size);
            startTime = System.nanoTime();
            bubbleSort(array.clone());
            endTime = System.nanoTime();
            System.out.println("Time: " + (endTime - startTime) / 1000 + " µs"); // Em microsegundos

            array = generateRandomArray(size);
            quicksort(array.clone()); // O tempo é calculado dentro do método quicksort
        }
    }
}