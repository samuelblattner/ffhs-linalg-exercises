import numpy as np

# Statics
PAGES = {
    'grumpy-cats': {
        'title': 'Grumpy Cats',
        'links-to': (
            'best-three-cat-sites',
        )
    },
    'fluffy-cats': {
        'title': 'Fluffy Cats',
        'links-to': (
            'best-three-cat-sites',
        )
    },
    'just-lol-cats': {
        'title': 'Just Lol-Cats',
        'links-to': (
            'cat-videos',
            'best-three-cat-sites',
        )
    },
    'cat-videos': {
        'title': 'Best cat videos on the planet',
        'links-to': (
            'grumpy-cats',
            'best-three-cat-sites'
        )
    },
    'best-three-cat-sites': {
        'title': 'The three best cat sites',
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

    @staticmethod
    def __fixed_length_string(text, length):
        """
        Utility method to extend a string to a fixed length.
        :param text: {str} Text to extend
        :param length: {int} Fixed length to extend to
        :return: {str} fixed-length string
        """
        return '{}{}'.format(text, ' ' * (length - len(text)))

    @staticmethod
    def __build_unit_vector(dimensions):
        return np.matrix([[1] for _ in range(0, dimensions)])

    @staticmethod
    def __build_adjacency_graph():
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

        return np.matrix(matrix_rows)

    @classmethod
    def __print_results(cls, result_vec):
        """
        Print the resulting weight vector.
        """

        # Print title
        print('\n\n{}\n{}\n'.format(
            RESULT_DISPLAY_HEADER,
            '=' * len(RESULT_DISPLAY_HEADER)
        ))

        longest_page_name = max([len(info.get('title', '')) for _, info in PAGES.items()])

        # Print table header
        table_header = '{}{}   {}'.format(
            'Rank  ',
            cls.__fixed_length_string(RESULT_TABLE_PAGE_LABEL, longest_page_name),
            RESULT_TABLE_RANK_VALUE_LABEL
        )
        print('{}\n{}'.format(
            table_header,
            '-' * len(table_header)
        ))

        # Create rank rows
        for i, (page_info, rank_value) in enumerate(sorted(zip(
                    PAGES.values(),
                    result_vec.getA1()
                ), key=lambda t: t[1], reverse=True)):

            page_name = page_info.get('title', '')

            print('{}{}   {}'.format(
                cls.__fixed_length_string(str(i + 1), len('Rank  ')),
                '{}{}'.format(page_name, ' ' * (longest_page_name - len(page_name))),
                rank_value))

        # Print bottom padding
        print(' ')

    @classmethod
    def execute_pageranking(cls, k=1000, d=1):
        """
        Execute the page ranking iterations.
        :param k: {int} Number of iterations to perform
        :param d: {float} Damping factor
        """
        unit_vec = cls.__build_unit_vector(len(PAGES.keys()))
        w = cls.__build_unit_vector(len(PAGES.keys()))
        adj = cls.__build_adjacency_graph()

        result_vec = (1 - d) * sum([d ** j * adj ** j * unit_vec for j in range(0, k - 1)]) + d ** k * adj ** k * w

        cls.__print_results(result_vec)


PageRanker.execute_pageranking()
