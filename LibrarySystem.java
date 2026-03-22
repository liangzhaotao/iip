import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibrarySystem {
    // 存储所有图书（按添加顺序）
    private List<Book> bookList = new ArrayList<>();
    // 存储唯一图书类型（自动去重）
    private Set<String> genreSet = new HashSet<>();
    // 根据图书ID快速查找（O(1)复杂度）
    private Map<Integer, Book> bookMap = new HashMap<>();

    // 添加图书：同时维护三个集合
    public void addBook(Book book) {
        bookList.add(book);
        genreSet.add(book.getGenre());
        bookMap.put(book.getId(), book);
    }

    // 使用Iterator安全删除包含关键词的图书
    public void removeBooksByKeyword(String keyword) {
        Iterator<Book> iterator = bookList.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().contains(keyword)) {
                // 从List中移除
                iterator.remove();
                // 从Map中移除
                bookMap.remove(book.getId());
                // 检查该类型是否还有其他图书，若无则从Set中移除
                boolean hasOtherBooks = false;
                for (Book b : bookList) {
                    if (b.getGenre().equals(book.getGenre())) {
                        hasOtherBooks = true;
                        break;
                    }
                }
                if (!hasOtherBooks) {
                    genreSet.remove(book.getGenre());
                }
            }
        }
    }

    // 打印当前所有集合状态
    public void displayStatus() {
        System.out.println("=== 图书列表 (ArrayList) ===");
        for (Book book : bookList) {
            System.out.println(book);
        }

        System.out.println("\n=== 唯一图书类型 (HashSet) ===");
        for (String genre : genreSet) {
            System.out.println(genre);
        }

        System.out.println("\n=== 图书ID映射 (HashMap) ===");
        for (Map.Entry<Integer, Book> entry : bookMap.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " -> " + entry.getValue());
        }
    }

    // 测试入口
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();

        // 添加测试数据
        library.addBook(new Book(1, "Java 核心技术", "技术"));
        library.addBook(new Book(2, "三体", "科幻"));
        library.addBook(new Book(3, "活着", "文学"));
        library.addBook(new Book(4, "Spring 实战", "技术"));
        library.addBook(new Book(5, "流浪地球", "科幻"));

        System.out.println("=== 初始状态 ===");
        library.displayStatus();

        // 测试删除关键词为"Java"的图书
        library.removeBooksByKeyword("Java");
        System.out.println("\n=== 删除关键词'Java'后状态 ===");
        library.displayStatus();
    }
}