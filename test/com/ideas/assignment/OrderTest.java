class OrderTest {
    private static final Store store = new Store();

    @BeforeAll
    public static void setStoreTestContext() {
        store.addBasicTaxAmountForStore(10d);
        store.addImportDutyTaxAmount(5d);
        store.addCategoriesWithNoTax(List.of("books", "food", "medical"));
    }

    @Test
    void getReceipt() {
        Item book = new Item(1, "Story book", 124.99, "books", false);
        Item cd = new Item(1, "Music CD", 149.99, "music", false);
        Item chocolate = new Item(1, "Choco bar", 40.85, "food", false);
        String receipt = store.placeOrderAndGetReceipt(List.of(book, cd, chocolate));
        assertEquals("1 Story book: 124.99,1 Music CD: 164.99,1 Choco bar: 40.85,Tax: 15.0,Total: 330.83", receipt);
    }

    @Test
    void getReceiptWithImportedItems() {
        Item importedPerfume = new Item(1, "Imported Perfume", 270.99, "beauty", true);
        Item perfume = new Item(1, "Perfume", 180.99, "beauty", false);
        Item pills = new Item(1, "Headache Pills", 19.75, "medical", false);
        Item chocolates = new Item(1, "Imported Chocolates", 210.25, "food", true);
        String receipt = store.placeOrderAndGetReceipt(List.of(importedPerfume, perfume, pills, chocolates));
        assertEquals("1 Imported Perfume: 311.64,1 Perfume: 199.09,1 Headache Pills: 19.75,1 Imported Chocolates: 220.76,Tax: 69.26,Total: 751.24", receipt);
    }
}