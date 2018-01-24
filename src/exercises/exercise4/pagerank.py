import numpy as np

# Statics

PAGES = {
    'grumpy-cats': {
        'name': 'Grumpy Cats',
        'links-to': (
            'best-three-cat-sites',
        )
    },
    'fluffy-cats': {
        'name': 'Fluffy Cats',
        'links-to': (
            'best-three-cat-sites',
        )
    },
    'just-lol-cats': {
        'name': 'Just Lol-Cats',
        'links-to': (
            'cat-videos',
            'best-three-cat-sites',
        )
    },
    'cat-videos': {
        'name': 'Best cat videos on the planet',
        'links-to': (
            'grumpy-cats',
            'best-three-cat-sites'
        )
    },
    'best-three-cat-sites': {
        'name': 'The three best cat sites',
        'links-to': (
            'grumpy-cats',
            'fluffy-cats',
            'just-lol-cats'
        )
    }
}

RESULT_DISPLAY_HEADER = 'Page Rank Results:'
RESULT_TABLE_PAGE_LABEL = 'Page name'
RESULT_TABLE_RANK_VALUE_LABEL = 'Page rank value'


class PageRanker:
    """
    Simple class to demonstrate the Page Rank Algorithm.
    """

    __adj_graph = None
    __weights = None
    __unit_vec = None

    @staticmethod
    def __fixed_length_string(text, length):
        """
        Utility method to extend a string to a fixed length.
        :param text: {str} Text to extend
        :param length: {int} Fixed length to extend to
        :return: {str} fixed-length string
        """
        return '{}{}'.format(text, ' ' * (length - len(text)))

    def __build_initial_vector(self):
        """
        Build the initial vector (all page weights = 1), and a unit vector
        """
        self.__weights = np.matrix([[1] for _ in PAGES.keys()])
        self.__unit_vec = np.matrix([[1] for _ in PAGES.keys()])

    def __build_adjacency_graph(self):
        """
        Build an adjacency matrix out of the page information provided
        in the PAGES static dictionary.
        """

        matrix_rows = []

        for tp, (tp_id, tp_info) in enumerate(PAGES.items()):

            row = [0] * len(PAGES.keys())

            for sp, (sp_id, sp_info) in enumerate(PAGES.items()):

                outbound_links = sp_info.get('links-to', ())
                if tp_id in outbound_links:
                    row[sp] = 1.0 / len(outbound_links)

            matrix_rows.append(row)

        self.__adj_graph = np.matrix(matrix_rows)

    def __print_results(self):
        """
        Print the resulting weight vector.
        """

        # Print title
        print('\n\n{}\n{}\n'.format(
            RESULT_DISPLAY_HEADER,
            '=' * len(RESULT_DISPLAY_HEADER)
        ))

        longest_page_name = max([len(info.get('name', '')) for _, info in PAGES.items()])

        # Print table header
        table_header = '{}{}   {}'.format(
            'Rank  ',
            self.__fixed_length_string(RESULT_TABLE_PAGE_LABEL, longest_page_name),
            RESULT_TABLE_RANK_VALUE_LABEL
        )
        print('{}\n{}'.format(
            table_header,
            '-' * len(table_header)
        ))

        # Create rank rows
        for i, (page_info, rank_value) in enumerate(sorted(zip(
                    PAGES.values(),
                    self.__weights.getA1()
                ), key=lambda t: t[1], reverse=True)):

            page_name = page_info.get('name', '')

            print('{}{}   {}'.format(
                self.__fixed_length_string(str(i + 1), len('Rank  ')),
                '{}{}'.format(page_name, ' ' * (longest_page_name - len(page_name))),
                rank_value))

        # Print bottom padding
        print(' ')

    def execute_pageranking(self, iterations=500, damping=0.85):
        """
        Execute the page ranking iterations.
        :param iterations: {int} Number of iterations to perform
        :param damping: {float} Damping factor
        """
        self.__build_initial_vector()
        self.__build_adjacency_graph()

        self.__weights = (1 - damping) * sum([damping**j * self.__adj_graph**j * self.__unit_vec for j in range(0, iterations - 1)]) + damping**iterations * self.__adj_graph**iterations * self.__weights

        self.__print_results()


pr = PageRanker()
pr.execute_pageranking()
