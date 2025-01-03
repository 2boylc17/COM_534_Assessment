class BookingSystemTest {
    val users = mutableListOf<User>()
    val rooms = mutableListOf<Room>()

    var currentUser: User? = null

    fun testSignup(username: String, password: String) {
        addUser(User(username, password, false))
    }

    fun testAddUser(u: User) {
        users.add(u)
    }

    fun testLogin(username: String, password: String) : User? {
        // singleOrNull() is a filter function which will either return a single instance of an object
        // which meets the criterion specified in the lambda, or null if no objects meet this criterion.
        currentUser = users.singleOrNull { it.username == username  && it.password == password }
        return currentUser
    }

    fun testLogout() {
        currentUser = null
    }

    fun testAddRoom(room: Room) {
        rooms.add(room)
    }

    fun testFindRoomByNumber(number: String) : Room? {
        return rooms.singleOrNull { it.number == number }
    }

    fun testFindRoomsByBuilding(building: String): List<Room> {
        return rooms.filter { it.building == building }
    }

    fun testFindRoomsByType(compType: String) : List<Room> {
        return rooms.filter { it.compType == compType }
    }

    fun testGetAllBookingsForRoomAndDay(room: String, day: String) : List<Booking>? {
        val room = findRoomByNumber(room)
        if(room != null) {
            return room.getBookingsByDay(day)
        }
        return null
    }

    fun testBookRoom(number: String, day: String, time: Int) : Boolean {
        val room = findRoomByNumber(number)
        if(room != null && currentUser != null) {
            // !! indicates that we know currentUser will not be null in our case
            // We have to do this as in multithreaded applications, another thread (process) might update
            // currentUser to null after this "if" statement has been executed
            return room.bookComputer(day, time, currentUser!!) != null
        }
        return false
    }

    fun testChangeComputerType(roomNumber: String, type: String) : Boolean {
        val room = findRoomByNumber(roomNumber)
        if(room != null) {
            room.compType = type
            return true
        }
        return false
    }

}