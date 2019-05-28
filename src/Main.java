import java.io.*;
import java.util.*;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
};

class Node {
    Point pt;
    int dist;

    Node(Point pt, int dist) {
        this.pt = pt;
        this.dist = dist;
    }
};

class Main {
    private static int rowCount;
    private static int columnCount;
    private static ArrayList<Integer> minDist = new ArrayList<>();
    //możliwe ruchy
    private static final int row[] = {-1, 0, 0, 1};
    private static final int col[] = {0, -1, 1, 0};

    private static boolean isValid(int mat[][], boolean visited[][],
                                   int row, int col) {
        return (row >= 0) && (row < rowCount) && (col >= 0) && (col < columnCount)
                && mat[row][col] == 1 && !visited[row][col];
    }


    private static void BFS(int mat[][], Point entry, Point exit) {

        boolean[][] visited = new boolean[rowCount][columnCount];

        Queue<Node> q = new ArrayDeque<>();

        visited[entry.x][entry.y] = true;
        q.add(new Node(entry, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            Point pt = node.pt;
            int dist = node.dist;

            if (pt.x == exit.x && pt.y == exit.y) {
                minDist.add(dist);
                break;
            }


            for (int i = 0; i < 4; i++) {
                if (isValid(mat, visited, pt.x + row[i], pt.y + col[i])) {
                    visited[pt.x + row[i]][pt.y + col[i]] = true;
                    q.add(new Node(new Point(pt.x + row[i], pt.y + col[i]), dist + 1));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("maze.txt");
        Scanner sc = new Scanner(file);
        rowCount = sc.nextInt();
        columnCount = sc.nextInt();
        int[][] mat = new int[rowCount][columnCount];
        String line;
        Point entry = null;
        ArrayList<Point> exit = new ArrayList<>();
        int i = -1;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (i != -1) {
                for (int j = 0; j < columnCount; j++) {
                    if (line.charAt(j) == 'X') {
                        mat[i][j] = 0;
                    } else if (line.charAt(j) == 'O') {
                        mat[i][j] = 1;
                    } else if (line.charAt(j) == 'S') {
                        mat[i][j] = 1;
                        entry = new Point(i, j);
                    } else if (line.charAt(j) == 'W') {
                        mat[i][j] = 1;
                        exit.add(new Point(i, j));
                    } else {
                        System.out.println("Błędny labirynt");
                    }
                }

            }
            i++;
        }

        for (Point p : exit) {
            BFS(mat, entry, p);
        }

        int minIndex = minDist.indexOf(Collections.min(minDist));
        System.out.print("TAK " + minDist.get(minIndex));
    }
}