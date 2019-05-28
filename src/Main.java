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
    private static final int leftRightMovement[] = {-1, 0, 0, 1};
    private static final int upDownMovement[] = {0, -1, 1, 0};

    private static boolean isValid(int maze[][], boolean visited[][], int row, int column) {
        return (row >= 0) && (row < rowCount) && (column >= 0) && (column < columnCount)
                && maze[row][column] == 1 && visited[row][column] == false;
    }

    private static void BFS(int mat[][], Point entry, Point exit) {
        boolean[][] visited = new boolean[rowCount][columnCount];
        Queue<Node> queue = new ArrayDeque<>();

        visited[entry.x][entry.y] = true;
        queue.add(new Node(entry, 0));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Point currentPnt = node.pt;
            int dist = node.dist;

            if (currentPnt.x == exit.x && currentPnt.y == exit.y) {
                minDist.add(dist);
                break;
            }

            for (int i = 0; i < 4; i++) {
                if (isValid(mat, visited, currentPnt.x + leftRightMovement[i], currentPnt.y + upDownMovement[i])) {
                    visited[currentPnt.x + leftRightMovement[i]][currentPnt.y + upDownMovement[i]] = true;
                    queue.add(new Node(new Point(currentPnt.x + leftRightMovement[i], currentPnt.y + upDownMovement[i]), dist + 1));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("maze.txt");
        Scanner sc = new Scanner(file);
        rowCount = sc.nextInt();
        columnCount = sc.nextInt();
        int[][] maze = new int[rowCount][columnCount];
        String line;
        Point entry = null;
        ArrayList<Point> exit = new ArrayList<>();
        int i = -1;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (i != -1) {
                for (int j = 0; j < columnCount; j++) {
                    if (line.charAt(j) == 'X') {
                        maze[i][j] = 0;
                    } else if (line.charAt(j) == 'O') {
                        maze[i][j] = 1;
                    } else if (line.charAt(j) == 'S') {
                        maze[i][j] = 1;
                        entry = new Point(i, j);
                    } else if (line.charAt(j) == 'W') {
                        maze[i][j] = 1;
                        exit.add(new Point(i, j));
                    } else {
                        System.out.println("Błędny labirynt");
                    }
                }
            }
            i++;
        }

        for (Point p : exit) {
            BFS(maze, entry, p);
        }

        if (minDist.isEmpty()) {
            System.out.println("NIE");
        } else {
            int minIndex = minDist.indexOf(Collections.min(minDist));
            System.out.print("TAK " + minDist.get(minIndex));
        }
    }
}