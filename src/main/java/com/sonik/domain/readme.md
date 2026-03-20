This package is the core of the application, meaning it can be extracted from the application, 
and used in a completely different environment.<br>
It is divided into several sub-packages:<br>
    <ul>
        <li><strong>model</strong> - Contains the JPA entities</li>
        <li><strong>repository</strong> - Contains the repository abstraction layer, 
        each JPA entity is accessed through its repository</li>
        <li><strong>exceptions</strong> - Contains exceptions related to the database</li>
    </ul>



