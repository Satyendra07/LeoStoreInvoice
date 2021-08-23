class OrderTest {
    private static final Store store = new Store();

    @BeforeAll
    public static void setStoreTestContext() {
        store.addBasicTaxAmount(10d);
        store.addImportDutyTaxAmount(5d);
        store.addCategoriesWithNoTax(List.of("books", "food", "medical"));
    }

    @Test
    void getReceiptWithoutImportedItems() {
        Item book = new Item(1, "Story book", 124.99, "books", false);
        Item cd = new Item(1, "Music CD", 149.99, "music", false);
        Item chocolate = new Item(1, "Choco bar", 40.85, "food", false);
        String receipt = store.placeOrderAndGetReceipt(List.of(book, cd, chocolate));
        assertEquals("1 Story book: 124.99,1 Music CD: 164.99,1 Choco bar: 40.85,Tax: 15.0,Total: 330.83", receipt);
    }

    @Test
    void getReceiptWithImportedItems() {
        Item importedPerfume = new Item(1, "Imported Perfume", 470.50, "beauty", true);
        Item chocolates = new Item(1, "Imported Chocolates", 105, "food", true);
        String receipt = store.placeOrderAndGetReceipt(List.of(importedPerfume, chocolates));
        assertEquals("1 Imported Perfume: 541.10,1 Imported Chocolates: 105.00,Tax: 75.60,Total: 646.10", receipt);
    }
}